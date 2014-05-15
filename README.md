FTP extractor DPU for UnifiedViews
==================================

Receiving files via FTP. Extractor takes these parameters:
- Server address
- Server port number (default 21)
- Username
- Password
- Initial directory - Start point for recursive directory listing and searching for files.
- File types - Process only files with defined extensions (xml, tar.gz, ...). Extension names must be comma separated. In case you don´t care about extensions, leave it blank and extractor process all files.
- Process gain only - Process all new files, which aren´t already cached.
- Process only cached files - New files aren´t going to be downloaded.

Received files shouldn´t have the same name, because FileDataUnit of UnifiedViews ignore their full path and overwrite them.

