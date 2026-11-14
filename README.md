# Campus Lost & Found Portal (with AI Matching)

Full stack app: **React (Vite)** + **Spring Boot** + **Spring Core/MVC** + **Spring Data REST** + **H2** + **GitHub Actions CI**.
Students report lost/found items; an AI text-similarity engine suggests likely matches.

## Run (needs Java 17+, Maven 3.8+, Node 18+)
Terminal 1 (backend):
    cd backend
    mvn spring-boot:run          # http://localhost:8080
Terminal 2 (frontend):
    cd frontend
    npm install
    npm run dev                  # http://localhost:5173

Handy URLs: http://localhost:8080/data/items (Spring Data REST), /data/items/search,
/api/stats, /api/items/1/matches, /h2-console (JDBC URL jdbc:h2:mem:lostfound)

## Syllabus mapping (use in viva)
| Unit | Where |
|---|---|
| 1 React: components, props, state, hooks, forms, validation | frontend/src (ItemForm: useState + useEffect live validation; App: lifting state) |
| 2 Spring Core: IoC, DI, bean lifecycle | KeywordMatchingService (constructor injection, @PostConstruct/@PreDestroy), MatchingService interface |
| 3 Config + MVC REST | AppConfig (@Configuration, @Bean, no XML), InsightController (@RestController, @PathVariable, @RequestParam, JSON) |
| 4 Spring Boot + Data REST | application.properties (externalized config), ItemRepository (@RepositoryRestResource, query methods), auto-config |
| 5 DevOps + Git | .github/workflows/ci.yml (CI pipeline), Git branches/commits |
| AI topic | Matching engine (stop-word removal + Jaccard similarity), AI-generated commit messages |

## 2-minute demo script
1. Show dashboard counters. 2. Report a LOST item (show live validation + char counter).
3. Click "AI Matches" on a lost item, show % score. 4. Mark resolved, counter updates.
5. Open /data/items in browser to show Spring Data REST. 6. Show the GitHub Actions green tick.

## Possible extensions
Swap KeywordMatchingService for an LLM-based one (same interface), add Spring Security login, MySQL instead of H2.
