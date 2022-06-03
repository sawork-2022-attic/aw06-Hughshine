1. Batch 负责大量数据的整理、存储（mysql）
2. Pos 改换从新的数据源读取数据（mysql）
3. Batch 和 Pos 项目放在一起会更好；应当尽量保证 Batch 的高性能

![](./aw06.png)