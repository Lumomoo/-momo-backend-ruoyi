# Repository Guidelines

## Project Structure & Module Organization
- `momo-admin` 为主启动模块，包含 Web API、配置与 `src/test` 单测代码。
- `momo-modules` 为业务模块（如 `system`、`health`、`job`、`workflow`）。
- `momo-common` 提供通用组件与基础能力封装。
- `momo-extend` 为可选扩展模块（监控、任务服务等）。
- `script` 存放 SQL、Docker 编排与运维脚本；`logs` 用于本地运行日志。

## Build, Test, and Development Commands
- `mvn -pl momo-admin -am spring-boot:run` 本地启动后端服务。
- `mvn clean package` 默认跳过测试打包（根 `pom.xml` 设置 `skipTests=true`）。
- `mvn -DskipTests=false -pl momo-admin test` 运行 JUnit 5 测试。
- `sh script/bin/ry.sh start|stop|restart|status` 管理打包后的 `momo-admin.jar`。
- `docker compose -f script/docker/docker-compose.yml up -d` 启动依赖服务。

## Coding Style & Naming Conventions
- 使用 UTF-8、LF、4 空格缩进；`*.yml/*.yaml/*.json` 为 2 空格（见 `.editorconfig`）。
- Java 包名以 `org.dromara` 开头；控制器 `*Controller`，服务接口 `*Service`，实现 `*ServiceImpl`，Mapper 为 `*Mapper`。
- 模型分层：`domain` 实体、`bo` 业务对象、`vo` 展示对象；MyBatis XML 在 `src/main/resources/mapper/**`。

## Testing Guidelines
- 测试集中在 `momo-admin/src/test/java/org/dromara/test`，基于 JUnit 5 + Spring Boot Test。
- 命名建议 `*Test` 或 `*UnitTest`，需要上下文时使用 `@SpringBootTest`。

## Commit & Pull Request Guidelines
- 提交信息常见格式为 `momo-backend-ruoyi: ...`，中文描述并可用 `1、...` 列表补充细节。
- PR 需说明目的、影响模块、关联 Issue/需求、测试结果，以及配置或 SQL 变更说明。

## Security & Configuration Tips
- 环境配置位于 `momo-admin/src/main/resources`，按 `application-*.yml` 区分环境。
- 初始化与升级脚本在 `script/sql`，多数据库版本分目录维护。

## Agent-Specific Instructions
- 新增方法、Controller、Service、Mapper、实体字段时请添加中文注释；前端文件新增方法同样需要中文注释。
- 新增 Python 脚本时，请在文档末尾提供拆分的命令行执行示例。
