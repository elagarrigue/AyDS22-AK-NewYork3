package ayds.newyork.songinfo.moredetails.model

import ayds.newyork.songinfo.moredetails.model.repository.broker.proxy.lastfm.LastFMProxy
import ayds.newyork.songinfo.moredetails.model.repository.broker.proxy.wikipedia.WikipediaProxy
import android.content.Context
import ayds.lisboa.lastfmdata.lastfm.LastFMInjector
import ayds.newyork.songinfo.moredetails.model.repository.ArtistInfoRepository
import ayds.newyork.songinfo.moredetails.model.repository.ArtistInfoRepositoryImpl
import ayds.newyork.songinfo.moredetails.model.repository.broker.InfoBroker
import ayds.newyork.songinfo.moredetails.model.repository.broker.InfoBrokerImpl
import ayds.newyork.songinfo.moredetails.model.repository.broker.proxy.ServiceProxy
import ayds.newyork.songinfo.moredetails.model.repository.broker.proxy.newyorktimes.NytProxy
import ayds.newyork.songinfo.moredetails.model.repository.local.card.LocalStorage
import ayds.newyork.songinfo.moredetails.model.repository.local.card.sqldb.CursorToArtistArticleMapperImpl
import ayds.newyork.songinfo.moredetails.model.repository.local.card.sqldb.LocalStorageImpl
import ayds.newyork.songinfo.moredetails.view.MoreDetailsView
import ayds.ny3.newyorktimes.NytInjector
import ayds.winchester1.wikipedia.WikipediaInjector
import kotlin.collections.ArrayList

object MoreDetailsModelInjector {

    private lateinit var moreDetailsModel: MoreDetailsModel

    fun getMoreDetailsModel(): MoreDetailsModel = moreDetailsModel

    fun initMoreDetailsModel(moreDetailsView: MoreDetailsView) {
        val localStorage: LocalStorage = LocalStorageImpl(
            moreDetailsView as Context, CursorToArtistArticleMapperImpl()
        )

        val proxyList: ArrayList<ServiceProxy> = arrayListOf(
            NytProxy(NytInjector.nytArticleService),
            LastFMProxy(LastFMInjector.lastFMService),
            WikipediaProxy(WikipediaInjector.wikipediaService),
        )

        val infoBroker: InfoBroker = InfoBrokerImpl(proxyList)

        val repository: ArtistInfoRepository =
            ArtistInfoRepositoryImpl(localStorage, infoBroker)

        moreDetailsModel = MoreDetailsModelImpl(repository)
    }
}