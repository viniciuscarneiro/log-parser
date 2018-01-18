CREATE TABLE access_log (id bigint NOT NULL AUTO_INCREMENT, date datetime, http_response_status varchar(255), ip varchar(255), request_method varchar(255), user_agent varchar(255), PRIMARY KEY (id)) ENGINE=MyISAM DEFAULT CHARSET=utf8 DEFAULT COLLATE=utf8_general_ci;
CREATE TABLE blocked_ip (ip varchar(255) NOT NULL, comment varchar(255), PRIMARY KEY (ip)) ENGINE=MyISAM DEFAULT CHARSET=utf8 DEFAULT COLLATE=utf8_general_ci;