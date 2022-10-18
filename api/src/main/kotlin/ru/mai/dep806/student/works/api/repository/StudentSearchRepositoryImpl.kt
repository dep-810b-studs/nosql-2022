package ru.mai.dep806.student.works.api.repository

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.json.jackson.JacksonJsonpMapper
import co.elastic.clients.transport.ElasticsearchTransport
import co.elastic.clients.transport.rest_client.RestClientTransport
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.springframework.stereotype.Repository
import ru.mai.dep806.student.works.api.model.PersistentStudent

@Repository("studentSearchRepository")
class StudentSearchRepositoryImpl : StudentSearchRepository {

    private var elasticSearchClient: ElasticsearchClient

    companion object {
        const val INDEX_NAME = "student"
    }

    constructor(){
        val restClient: RestClient = RestClient.builder(
            HttpHost("localhost", 9200)
        ).build()
        val mapper = jsonMapper {
            addModule(kotlinModule())
        }
        val transport: ElasticsearchTransport = RestClientTransport(
            restClient, JacksonJsonpMapper(mapper)
        )

        elasticSearchClient = ElasticsearchClient(transport)
    }


    override fun add(student: PersistentStudent) {
        elasticSearchClient.index{
            it.index(INDEX_NAME)
            it.document(student)
        }
    }

    override fun update(student: PersistentStudent) {
        val searchResult = elasticSearchClient.search({requestBuilder ->
            requestBuilder
                .index(INDEX_NAME)
                .query { queryBuilder ->
                    queryBuilder.match {
                        it
                            .field("id")
                            .query(student.id!!.toLong())
                    }
                }
        }, PersistentStudent::class.java)

        val studentElasticId = searchResult.hits().hits().firstNotNullOf { it.id() }

        elasticSearchClient.index{
            it.index(INDEX_NAME)
            it.id(studentElasticId)
            it.document(student)
        }
    }

    override fun delete(id: Int) {
        elasticSearchClient.deleteByQuery {requestBuilder ->
            requestBuilder.index(INDEX_NAME)
            requestBuilder.query {queryBuilder ->
                queryBuilder.match {
                    it
                        .field("id")
                        .query(id.toLong())
                }
            }
        }
    }

    override fun find(filter: String): List<PersistentStudent> {
        val searchResponse = elasticSearchClient.search({requestBuilder ->
                requestBuilder
                    .index(INDEX_NAME)
                    .query { queryBuilder ->
                        queryBuilder.queryString {
                            it
                                .defaultField("name")
                                .query("*$filter*")
                        }
                    }
                    .sort { builder ->
                        builder.field { it.field("id") }
                    }
        }, PersistentStudent::class.java)

        return searchResponse
            .hits()
            .hits()
            .mapNotNull { it.source() }
    }

}
