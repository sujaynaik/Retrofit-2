package com.testretrofit.util;

import com.testretrofit.model.Customer;
import com.testretrofit.model.CustomerDetail;
import com.testretrofit.model.MyResponse;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class RestClient {

    private static String TAG = "Retrofit";

    private static final HttpLoggingInterceptor.Level LOG_LEVEL
            = Constant.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE;

    /**
     * @return REST Adapter for API's
     */
    private static Retrofit getRestAdapter() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(LOG_LEVEL);
        OkHttpClient client = new OkHttpClient.Builder()
                .followRedirects(true)
                .readTimeout(160, TimeUnit.SECONDS)
                .connectTimeout(160, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        
        /*OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        OkHttpClient client = httpClient.build();

        return new Retrofit.Builder()
                .baseUrl(Constant.API_REQUEST)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();*/
    }

    public static TestApi getTestApi() {
        return getRestAdapter().create(TestApi.class);
    }

    private static Retrofit getRestAdapter(final String token) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(LOG_LEVEL);
        OkHttpClient client = new OkHttpClient.Builder()
                .followRedirects(true)
                .readTimeout(160, TimeUnit.SECONDS)
                .connectTimeout(160, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        
        /*OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Utils.loge(TAG, "**********************       REQUEST START     **********************");
                Utils.loge(TAG, "REQUEST URL -> " + original.url());
                Utils.loge(TAG, "REQUEST HEADERS -> " + original.headers());
                Utils.loge(TAG, "**********************       REQUEST END     **********************");

                // Customize the request
                Request request = original.newBuilder()
                        .addHeader(Constant.TAG_TOKEN, token)
                        .method(original.method(), original.body())
                        .build();

                Response response = chain.proceed(request);
                Utils.loge(TAG, "**********************       RESPONSE START     **********************");
                Utils.loge(TAG, "RESPONSE CODE -> " + response.code());
                Utils.loge(TAG, "RESPONSE HEADERS -> " + response.headers());
                Utils.loge(TAG, "**********************       RESPONSE END     **********************");

                // Customize or return the response
                return response;
            }
        });
        OkHttpClient client = httpClient.build();

        return new Retrofit.Builder()
                .baseUrl(Constant.API_REQUEST)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();*/
    }

    public static TestApi getTestApi(String token) {
        return getRestAdapter(token).create(TestApi.class);
    }

    /**
     * All API calls
     */
    public interface TestApi {
        @PUT(Constant.API_PUT_CUSTOMER)
        Call<MyResponse> createCustomer(@Header(Constant.TAG_TOKEN) String token,
                                        @Header("userId") String userId,
                                        @Header("branchId") int branchId,
                                        @Body Customer customer);

        @GET(Constant.API_GET_CUSTOMER)
        Call<CustomerDetail> getCustomer(@Path("customerid") int customerid);
    }
}
