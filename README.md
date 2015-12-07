# News Spider

這是物件導向程式設計課程的作業，撰寫一隻爬蟲去爬取資源。我設計的這隻程式，以 BBC 為目標，可以設定要爬取的 Channel，會自動將文章擷取下來並存成JSON檔案。

# 目標

可以存取任何的新聞網站，透過修改參數。目前僅適用 BBC，此外會有部分文章因為版面配置不同無法擷取。

# To Do

- 使用 ThreadPool
- 使用 LocalDateTime
- 自動偵測是否需要使用 baseUrl
- 處理各種錯誤問題
- User Agent
- sleep
- 可能有多種版面配置，須設置多種 selector
- 可能需要select attr
- 使用 list.map 取代 for
- 增進 isFinish 效能
- callable
