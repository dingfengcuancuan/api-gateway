package com.zhanghui.controller;

import com.zhanghui.api.bean.sign.ApiKey;
import com.zhanghui.api.bean.sign.ApiKeyContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Handles requests for the application home page.
 */
@Controller
public class IndexController {
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );

		//模拟加载 签名apiKey
		intiApiKey();

		return "index";
	}

	/**
	 * 初始化apiKey
	 */
	public void intiApiKey(){
		// 项目启动后 会访问两次这个地址
		if(ApiKeyContainer.apiKeyMap().size()==0) {
			ApiKey apiKey1 = new ApiKey();
			apiKey1.setAppId("app1");
			apiKey1.setPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnLbS4NKB4K6lSwAruObksqJpcrs7t8w51Y38I9ix9r7WCBMiAjRxWu8DNnr2t5ya/XXoioHqyMH3oEkiqsUh3FX1pG/4N/9/hRtFDJqpkCo4Z/D76wEehY+y+k8sGEO+dO8m8bNbmfpcIGDwuPl3qVBFeyJQwPguuWd32/xtzLcYk2C/Eec25++ElHobGvLkRTHHlXeZ+v8IYCIPd058bQssnFMw7nn+Lgr2kQK/2gPQ8gXs8mi1712i/dgoo8bzw9mhzkFnWXExRqzgoXH5DfIMWnlRR4l1u2qXXPU+hIKFua2776qHREmeqPkKiHA2zoMc9LVydVDTGovJjSn00wIDAQAB");
			apiKey1.setPrivateKey("MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCcttLg0oHgrqVLACu45uSyomlyuzu3zDnVjfwj2LH2vtYIEyICNHFa7wM2eva3nJr9deiKgerIwfegSSKqxSHcVfWkb/g3/3+FG0UMmqmQKjhn8PvrAR6Fj7L6TywYQ7507ybxs1uZ+lwgYPC4+XepUEV7IlDA+C65Z3fb/G3MtxiTYL8R5zbn74SUehsa8uRFMceVd5n6/whgIg93TnxtCyycUzDuef4uCvaRAr/aA9DyBezyaLXvXaL92CijxvPD2aHOQWdZcTFGrOChcfkN8gxaeVFHiXW7apdc9T6EgoW5rbvvqodESZ6o+QqIcDbOgxz0tXJ1UNMai8mNKfTTAgMBAAECggEAaWS+/7oy5JMv0Pflb63Awq5dcm6fI+XyQ0ABAW6biREgyj9r0MXKMlip9XrO7/8fcTRZ1sS2zJs+WQq2iNiZBCC/Wf5/ldF7xl3nmylsverXEnhMQ+j1yOcyfArA4fS1YtjvfP+drDlmudPYMN59bl3wzHXwj0aZzdDbGBR1F4Y+CzgRObq5092acJz/foUO5SAg/+KLhmQQ28AvBhXPTgV467ER34pDHexCgAnsLsH4KVCPxsbW0eh7vaIgUjWQ/sTAfRzFW5CLJRYO1xH6mDS42dImiUJBiWiz2fK4FRrSM05QXCHkKfXb1DpMnvrNyHyCDZfshRp7UsZoxtVeaQKBgQDvWrX7JNb3Km5JncdKRq+I8lqx+d750Ffj0Ln2wTR0lahonJyF+YL4yuP5R8+sCeFRjcPWMUkZ2oSxxT6gaHRl0Z2fJXqwuCaO9NKq9C03olPmiFXM/H4knXsr1urfcRmE5sg3k0kcmtDnxoyDBBb3w8SRb8p0jqRl/NspDq9IJwKBgQCnnNj/JgCZJkWnK7JPTqnjTlavkV1Zicz89dvQ9Eq7Ff0e/BJiEi+DrndSP2v2YOZEJr0LOpIxJwaNCgea67t055/IlsBqHBu0cBtGQ7i2ykd/3Q1v+q0dEKf3C92lvwcsLRQo01gjnDMRiiSkIf0cYdxs66wgzAxh4xd9BvwNdQKBgAEVrydxNkwudAt/XIzYnykGuCSAVmNZb1yH8J/Oplc06mt28jqlM9O+z6OskKNd9BhzhQSuen9Ufy9zDmKZtpVTitxSxiiQ3RPeximiK6ZJ5QlxarogFs5BrHI5ah0THSN+DEA8OaOYjAPQ4Ygid5wt1fE2yXsXvmT77V5VQ7QXAoGALDRNdl0LY1iYnhIEIK5aV7xdWEg6GlchXMVqh50l6FlQPE+2eW7aYRwuE97uFjhQAkFFMiTsUVI9hAzVHKJ2+cnsdfZsII/xLpEyYEGUAYEvgiVGWfX+md++rITQm9nZhmkNHAdBA4M6ZLHOIAtmuYmFnKQ67RtjWJ2PoEWrS9ECgYBW6QtwiRxoz/Xes9jHRmShVcDfwq210Qubmd81uUtDy/lVOY99qCb4rNsTu3RVrtMubD9syvivDroE6NtFrDBNHAtRaGB77Ivs+WdMdhDzNwGxVfgt6h/qJoY05+NmVPe3ExtC2fs8EWnZkXPbs2qurFxdvhBjxHIQHQwV54GaZQ==");

			ApiKeyContainer.addApiKey(apiKey1);

			ApiKey apiKey2 = new ApiKey();
			apiKey2.setAppId("app2");
			apiKey2.setPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqdHwFgxfFqqyGOmcHEdYmMvtxaw6aMGVKopqVjS/8xdfIsDtkHvaGWwq5EaJKQxZSC/MfNeTSBI6AjCVI+4dtMPdr15HocaYGeCN7o+6VlKiKwaGFA9lk2ynMqjtIL0A2/eqreQAGd7Y7ogqYrpOsaJ/VnH7SmpGT3lMUK6qVSyyv6sFrp2nT87PVY/QuyjI+P9YWMIcv33P7whGPSwwO1JHZgdCIo4QQaHc7BY1JCR5MdhesebwXZ6F4j2APVPnt974VOhD49iz4R5AuK1VlRecqDD5kSdc1PcrvoEtFE2NBSzUqS1xPkKWbYsN05ikHENKbud4xHuCWHHVCZ+lHwIDAQAB");
			apiKey2.setPrivateKey("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCp0fAWDF8WqrIY6ZwcR1iYy+3FrDpowZUqimpWNL/zF18iwO2Qe9oZbCrkRokpDFlIL8x815NIEjoCMJUj7h20w92vXkehxpgZ4I3uj7pWUqIrBoYUD2WTbKcyqO0gvQDb96qt5AAZ3tjuiCpiuk6xon9WcftKakZPeUxQrqpVLLK/qwWunadPzs9Vj9C7KMj4/1hYwhy/fc/vCEY9LDA7UkdmB0IijhBBodzsFjUkJHkx2F6x5vBdnoXiPYA9U+e33vhU6EPj2LPhHkC4rVWVF5yoMPmRJ1zU9yu+gS0UTY0FLNSpLXE+QpZtiw3TmKQcQ0pu53jEe4JYcdUJn6UfAgMBAAECggEAVjJSa86RwV6ZEZ4Z+Byassvn/4rhBuUjgrlJKkKSH6CAUJKewtSqqnbF+qJ24lf7RXHFmKSPhGiD+zKPsPIfL+6vKmFveITmFVRulmVrzn8ZAz/HdI0wILlo0IT//7sPoZc/kg4FoxwC/mgtI3mvkeiKO+WVCHuS/qnx+438tUgqLLhoMWyrPJt4zSpoVOyo8oDUVK4MxE6e3uIRbgpkLQGKUZJbwu5ALzYCXzuQ2mIvfzJd0JCC+iIMNlBtiSroo0DLwRydXVLqb2A9MWJu3hzoLefMtUHl9utfoEFfRhnRtwD+Nj+eTey9q6yg5FgjRcFZZdRDTO/JI2kBfzjHaQKBgQDvcIENo1cEKTr57K6unZPkulGDdvWb2sukeSJ2uQ1isfRjdsu4s9ISPheUuGFRyywyQO0iy9rsDObVM0Ebj5W/7p1w2MDID2k6AAXopA6AqxkdlST2ZLzV/hbMrhgFRm+fRExF4LXX7f0t0MjCIQAkR+144ISi7TBRAHf+vvL1dQKBgQC1kMIddlNC0BY+PJS3054YwnKlBpjchDn302D2tbiSDYlE2q/0u4fIgGUbYUIktUm+2o5Wkp1RD3EYGrLiZmZHYj6itKVl+WtQhRovydIDrVR+k1vX+gb2+dX0azyAIfU7otPRz7nBVcnlAPC2VpWzdhKUU8UcAHkg1a9xVjVZwwKBgQDUaD9z88TLwi+5aoeEC/IY5FhPgYZcnnotC/DQ1VTUiv08BzSuQ4dF+6M7oV1kDBLG+eXkCmfJI6Da3T8SzguQWoN8iixqzZERjNexC2h6KE59ecNyeOc0BtX++SoB8/wtk47+JlvqlAsXS7Hs1Q2lIdG+e53uUu+bzAinConJyQKBgC/Qln5Byf1/ZUwwkg2zKiBlHOH+0cTJAacVQKTYCGpWLD84xDvUIxcXUQkb3Npj767WScPcypctVOA/L7rVsru70Ljyj9hCDhPfaZ0KLkePnv/SeM5w5uKlusWqc/1edAGniNbpOIcEu62WSM4MEVJ4lr3/LZqJOVz/STu0s0oXAoGAMHUTc3mcox8fYAyuQDuh0XEqb9stscSxSuoRdb56aV2kJ7Jpia+vKjOPk8+CTlvKu68CGwBVNcedvU4Cmc79SwmC4GOTpUal9uwUrsKaQ3V8wWT0QZjo+ue1L7b31B67kIGK1Y6iGJ1MwodEf+yZMrNcXNqTj9Z4f+XgzoXYoKs=");

			ApiKeyContainer.addApiKey(apiKey2);
		}
	}
}
