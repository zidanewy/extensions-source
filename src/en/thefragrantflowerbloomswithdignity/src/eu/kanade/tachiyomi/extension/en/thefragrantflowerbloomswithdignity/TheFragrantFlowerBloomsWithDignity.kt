package eu.kanade.tachiyomi.extension.en.thefragrantflowerbloomswithdignity

import keiyoushi.annotation.Source
import eu.kanade.tachiyomi.source.model.FilterList
import eu.kanade.tachiyomi.source.model.MangasPage
import eu.kanade.tachiyomi.source.model.Page
import eu.kanade.tachiyomi.source.model.SChapter
import eu.kanade.tachiyomi.source.model.SManga
import eu.kanade.tachiyomi.source.online.HttpSource
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.jsoup.Jsoup

@Source
class TheFragrantFlowerBloomsWithDignity : HttpSource() {

    override val name = "The Fragrant Flower Blooms with Dignity"
    override val baseUrl = "https://fragrantflowerbloomswithdignity.com"
    override val lang = "en"
    override val supportsLatest = true

    override val client: OkHttpClient = network.cloudflareClient

    // ── Popular manga ─────────────────────────────────────────────────────

    override fun popularMangaRequest(page: Int): Request = GET("$baseUrl/manga/?page=$page", headers)

    override fun popularMangaParse(response: Response): MangasPage {
        val document = Jsoup.parse(response.body.string())
        val mangas = document.select("TODO: selector for manga cards").map { el ->
            SManga.create().apply {
                title = el.select("TODO: title selector").text()
                setUrlWithoutDomain(el.select("a").attr("href"))
                thumbnail_url = el.select("img").attr("src")
            }
        }
        val hasNextPage = document.select("TODO: next page selector").isNotEmpty()
        return MangasPage(mangas, hasNextPage)
    }

    // ── Latest manga ──────────────────────────────────────────────────────

    override fun latestUpdatesRequest(page: Int): Request = GET("$baseUrl/manga/?page=$page&order=update", headers)

    override fun latestUpdatesParse(response: Response) = popularMangaParse(response)

    // ── Search ────────────────────────────────────────────────────────────

    override fun searchMangaRequest(page: Int, query: String, filters: FilterList): Request = GET("$baseUrl/?s=$query&page=$page", headers)

    override fun searchMangaParse(response: Response) = popularMangaParse(response)

    // ── Manga details ─────────────────────────────────────────────────────

    override fun mangaDetailsParse(response: Response): SManga {
        val document = Jsoup.parse(response.body.string())
        return SManga.create().apply {
            title = document.select("TODO: title").text()
            author = document.select("TODO: author").text()
            description = document.select("TODO: synopsis").text()
            thumbnail_url = document.select("TODO: cover img").attr("src")
            status = when (document.select("TODO: status").text().lowercase()) {
                "ongoing" -> SManga.ONGOING
                "completed" -> SManga.COMPLETED
                else -> SManga.UNKNOWN
            }
        }
    }

    // ── Chapter list ──────────────────────────────────────────────────────

    override fun chapterListParse(response: Response): List<SChapter> {
        val document = Jsoup.parse(response.body.string())
        return document.select("TODO: chapter list selector").map { el ->
            SChapter.create().apply {
                name = el.select("TODO: chapter name").text()
                setUrlWithoutDomain(el.select("a").attr("href"))
            }
        }
    }

    // ── Page list ─────────────────────────────────────────────────────────

    override fun pageListParse(response: Response): List<Page> {
        val document = Jsoup.parse(response.body.string())
        return document.select("TODO: image selectors").mapIndexed { i, el ->
            Page(i, imageUrl = el.attr("src").ifEmpty { el.attr("data-src") })
        }
    }

    override fun imageUrlParse(response: Response) = ""

    override fun getFilterList() = FilterList()
}
