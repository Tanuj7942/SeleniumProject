/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.seleniumproject;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

/**
 *
 * @author 23379
 */
public class WebScrapping {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            WebDriverManager.chromedriver().setup();

            ChromeDriver driver = new ChromeDriver();
            driver.get("https://tradestat.commerce.gov.in/eidb/ecntcomq.asp");

            List<WebElement> years = driver.findElements(By.xpath("/html/body/div/div[2]/div/form/table[1]/tbody/tr[1]/td[2]/select/option"));
            for (int i = 0; i < years.size(); i++) {
                WebElement countries = driver.findElement(By.name("cntcode"));
                WebElement hsCode = driver.findElement(By.name("hslevel"));
                WebElement allYear = driver.findElement(By.name("yy1"));

                Select select1 = new Select(countries);
                Select select2 = new Select(hsCode);
                Select select3 = new Select(allYear);

                select3.selectByIndex(i);
                select1.selectByIndex(0);
                select2.selectByValue("8");
                driver.findElement(By.id("radioDALL")).click();

                BufferedWriter writer = new BufferedWriter(new FileWriter("C://Users//23379.DESKTOP-CL7RQ2N//Documents//Selenium Testing//DepartmentOfCommerce(" + select1.getFirstSelectedOption().getText() + ")" + select3.getFirstSelectedOption().getText() + ".csv"));

                driver.findElement(By.xpath("/html/body/div/div[2]/div/form/p/font/input")).click();

                List<WebElement> rowElements = driver.findElements(By.xpath("/html/body/div/div[2]/div/table/tbody/tr"));

                List<WebElement> coloumnElements = driver.findElements(By.xpath("/html/body/div/div[2]/div/table/tbody/tr[2]/td"));
                for (int j = 2; j < rowElements.size(); j++) {
                    for (int k = 1; k < coloumnElements.size(); k++) {
                        writer.write('\u0022' + driver.findElement(By.xpath("/html/body/div/div[2]/div/table/tbody/tr[" + j + "]/td[" + k + "]")).getText() + '\u0022' + ",");
                    }
                    writer.write("\n");
                }
                writer.close();
                driver.navigate().back();
            }
            driver.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
