const json = { 'Content-Type': 'application/json' }
export const getItems = async () =>
  (await (await fetch('/data/items?sort=createdAt,desc&size=200')).json())._embedded?.items ?? []
export const getStats = async () => (await fetch('/api/stats')).json()
export const getMatches = async (id) => (await fetch(`/api/items/${id}/matches`)).json()
export const searchItems = async (q) => (await fetch(`/api/search?q=${encodeURIComponent(q)}`)).json()
export const addItem = (item) => fetch('/data/items', { method: 'POST', headers: json, body: JSON.stringify(item) })
export const resolveItem = (id) => fetch(`/data/items/${id}`, { method: 'PATCH', headers: json, body: JSON.stringify({ status: 'RESOLVED' }) })
export const deleteItem = (id) => fetch(`/data/items/${id}`, { method: 'DELETE' })
