@echo on
echo cd D:\
cd jar
java -jar selenium-server-standalone-3.4.0.jar -port 4444 -role node -hub http://10.10.105.228/grid/register -port 4444 -browser browserName="chrome" -Dwebdriver.chrome.driver="D:/Selenium/driver/chromedriver.exe"
pause>null