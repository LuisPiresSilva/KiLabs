package net.luispiressilva.kilabs_luis_silva.network.flickr

import io.reactivex.Single
import net.luispiressilva.kilabs_luis_silva.FLICKR_KEY
import net.luispiressilva.kilabs_luis_silva.network.flickr.schema.ServerResponse
import net.luispiressilva.kilabs_luis_silva.network.flickr.schema.metadata.PhotoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Luis Silva on 06/03/2019.
 */

private const val FORMAT = "format=json"
private const val NOJSONCALLBACK = "nojsoncallback=1"

interface FlickrAPI {

    //    1: bad value for jump_to, must be valid photo id.
//    100: Invalid API Key
//    The API key passed was not valid or has expired.
//    105: Service currently unavailable
//    The requested service is temporarily unavailable.
//    106: Write operation failed
//    The requested operation failed due to a temporary issue.
//    111: Format "xxx" not found
//    The requested response format was not found.
//    112: Method "xxx" not found
//    The requested method was not found.
//    114: Invalid SOAP envelope
//    The SOAP envelope send in the request could not be parsed.
//    115: Invalid XML-RPC Method Call
//    The XML-RPC request document could not be parsed.
//    116: Bad URL found
//    One or more arguments contained a URL that has been used for abuse on Flickr.
    @GET("rest/?method=flickr.photos.getRecent&api_key=$FLICKR_KEY&$FORMAT&$NOJSONCALLBACK")
    fun getRecent(
        @Query("extras") extras: String
    ): Single<Response<ServerResponse>>


    //    1: Too many tags in ALL query
//    When performing an 'all tags' search, you may not specify more than 20 tags to join together.
//    2: Unknown user
//    A user_id was passed which did not match a valid flickr user.
//    3: Parameterless searches have been disabled
//    To perform a search with no parameters (to get the latest public photos, please use flickr.photos.getRecent instead).
//    4: You don't have permission to view this pool
//    The logged in user (if any) does not have permission to view the pool for this group.
//    5: User deleted
//    The user id passed did not match a Flickr user.
//    10: Sorry, the Flickr search API is not currently available.
//    The Flickr API search databases are temporarily unavailable.
//    11: No valid machine tags
//    The query styntax for the machine_tags argument did not validate.
//    12: Exceeded maximum allowable machine tags
//    The maximum number of machine tags in a single query was exceeded.
//    17: You can only search within your own contacts
//    The call tried to use the contacts parameter with no user ID or a user ID other than that of the authenticated user.
//    18: Illogical arguments
//    The request contained contradictory arguments.
//    100: Invalid API Key
//    The API key passed was not valid or has expired.
//    105: Service currently unavailable
//    The requested service is temporarily unavailable.
//    106: Write operation failed
//    The requested operation failed due to a temporary issue.
//    111: Format "xxx" not found
//    The requested response format was not found.
//    112: Method "xxx" not found
//    The requested method was not found.
//    114: Invalid SOAP envelope
//    The SOAP envelope send in the request could not be parsed.
//    115: Invalid XML-RPC Method Call
//    The XML-RPC request document could not be parsed.
//    116: Bad URL found
//    One or more arguments contained a URL that has been used for abuse on Flickr.
    @GET("rest/?method=flickr.photos.search&api_key=$FLICKR_KEY&$FORMAT&$NOJSONCALLBACK")
    fun getSearch(
        @Query("extras") extras: String,
        @Query("text") search: String
    ): Single<Response<ServerResponse>>


    //    1: PhotoResponse not found
//    The photo id was either invalid or was for a photo not viewable by the calling user.
//    2: Permission denied
//    The owner of the photo does not want to share EXIF data.
//    100: Invalid API Key
//    The API key passed was not valid or has expired.
//    105: Service currently unavailable
//    The requested service is temporarily unavailable.
//    106: Write operation failed
//    The requested operation failed due to a temporary issue.
//    111: Format "xxx" not found
//    The requested response format was not found.
//    112: Method "xxx" not found
//    The requested method was not found.
//    114: Invalid SOAP envelope
//    The SOAP envelope send in the request could not be parsed.
//    115: Invalid XML-RPC Method Call
//    The XML-RPC request document could not be parsed.
//    116: Bad URL found
//    One or more arguments contained a URL that has been used for abuse on Flickr.
    @GET("rest/?method=flickr.photos.getExif&api_key=$FLICKR_KEY&$FORMAT&$NOJSONCALLBACK")
    fun getExif(
        @Query("photo_id") photo_id: String
    ): Single<Response<PhotoResponse>>
}