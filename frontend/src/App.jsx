import { useState, useEffect, useCallback } from 'react'
import { getItems, getStats, searchItems } from './api'
import ItemForm from './ItemForm'
import ItemCard from './ItemCard'

export default function App() {
  const [items, setItems] = useState([])
  const [stats, setStats] = useState({})
  const [filter, setFilter] = useState('ALL')
  const [query, setQuery] = useState('')

  const load = useCallback(async () => {
    setItems(query ? await searchItems(query) : await getItems())
    setStats(await getStats())
  }, [query])

  useEffect(() => { load() }, [load])

  const shown = items.filter((i) => filter === 'ALL' || i.type === filter)

  return (
    <div className="wrap">
      <h1>Campus Lost &amp; Found</h1>
      <div className="stats">
        <div>Lost open<b>{stats.lostOpen}</b></div>
        <div>Found open<b>{stats.foundOpen}</b></div>
        <div>Reunited<b>{(stats.lostResolved ?? 0) + (stats.foundResolved ?? 0)}</b></div>
      </div>
      <div className="layout">
        <ItemForm onSaved={load} />
        <section>
          <div className="row">
            <input placeholder="Search items..." value={query} onChange={(e) => setQuery(e.target.value)} />
            {['ALL', 'LOST', 'FOUND'].map((f) => (
              <button key={f} className={filter === f ? 'active' : ''} onClick={() => setFilter(f)}>{f}</button>
            ))}
          </div>
          {shown.map((i) => <ItemCard key={i.id} item={i} onChange={load} />)}
        </section>
      </div>
    </div>
  )
}
