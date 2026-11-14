import { useState } from 'react'
import { getMatches, resolveItem, deleteItem } from './api'

export default function ItemCard({ item, onChange }) {
  const [matches, setMatches] = useState(null)
  const id = item.id
  return (
    <div className={`card item ${item.status === 'RESOLVED' ? 'done' : ''}`}>
      <span className={`tag ${item.type}`}>{item.type}</span>
      <h4>{item.title}</h4>
      <p>{item.description}</p>
      <small>{item.category} | {item.location} | {item.contact}</small>
      {item.status === 'OPEN' && (
        <div className="row">
          <button onClick={async () => setMatches(await getMatches(id))}>AI Matches</button>
          <button onClick={async () => { await resolveItem(id); onChange() }}>Mark resolved</button>
          <button className="danger" onClick={async () => { await deleteItem(id); onChange() }}>Delete</button>
        </div>
      )}
      {matches && (
        <ul className="matches">
          {matches.length === 0 && <li>No likely matches yet</li>}
          {matches.map((m) => (
            <li key={m.item.id}><b>{m.score}%</b> {m.item.title} - {m.item.contact}</li>
          ))}
        </ul>
      )}
    </div>
  )
}
