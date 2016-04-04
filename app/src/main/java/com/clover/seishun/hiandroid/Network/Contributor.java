package com.clover.seishun.hiandroid.Network;

/**
 * Created by heaun.b on 2016. 4. 3..
 */
public class Contributor {
    public String login              ; //JakeWharton",
    public int    id                 ; //66577,
    public String avatar_url         ; //https://avatars.githubusercontent.com/u/66577?v=3",
    public String gravatar_id        ; //",
    public String url                ; //https://api.github.com/users/JakeWharton",
    public String html_url           ; //https://github.com/JakeWharton",
    public String followers_url      ; //https://api.github.com/users/JakeWharton/followers",
    public String following_url      ; //https://api.github.com/users/JakeWharton/following{/other_user}",
    public String gists_url          ; //https://api.github.com/users/JakeWharton/gists{/gist_id}",
    public String starred_url        ; //https://api.github.com/users/JakeWharton/starred{/owner}{/repo}",
    public String subscriptions_url  ; //https://api.github.com/users/JakeWharton/subscriptions",
    public String organizations_url  ; //https://api.github.com/users/JakeWharton/orgs",
    public String repos_url          ; //https://api.github.com/users/JakeWharton/repos",
    public String events_url         ; //https://api.github.com/users/JakeWharton/events{/privacy}",
    public String received_events_url; //https://api.github.com/users/JakeWharton/received_events",
    public String type               ; //User",
    public boolean site_admin        ; //false,
    public int contributions         ; //747

    public Contributor(){
        //jackson 은 파라미터가 없는 생성자를 생성해서 초기화 해줘야 한다
        /**
         * onFailure : No suitable constructor found for type
         * [simple type, class com.clover.seishun.hiandroid.Network.RetrofitLibraryActivity$Contributor]:
         * can not instantiate from JSON object
         * (missing default constructor or creator, or perhaps need to add/enable type information?)
         *
         * //jackson 은 파라미터가 없는 생성자를 생성해서 초기화 해줘야 한다*/

        login = null;
        contributions = 0;
        id = 0;
        site_admin = false;
        avatar_url = null;
    }
    public Contributor(String login, int id, int contributions){
        this.login = login;
        this.id = id;
        this.contributions = contributions;
    }
}
