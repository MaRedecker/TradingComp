package Generators;

import java.util.ArrayList;
import java.util.List;

import model.Article;

public class ArticleGenerator {
	
	public static List<Article> getTestArticles()
	{
		List<Article> articles = new ArrayList<Article>();
		articles.add(new Article("Apple", 0, 1));
		articles.add(new Article("Banana", 1, 1.5));
		articles.add(new Article("Cereal", 2, 0.5));
		articles.add(new Article("Date", 3, 0.8));
		articles.add(new Article("Endive", 4, 1.8));
		
		return articles;
	}

}
