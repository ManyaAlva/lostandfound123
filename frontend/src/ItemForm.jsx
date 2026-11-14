import { useState, useEffect } from 'react'
import { addItem } from './api'

const empty = { title: '', description: '', category: 'Bags', location: '', type: 'LOST', contact: '' }

export default function ItemForm({ onSaved }) {
  const [form, setForm] = useState(empty)
  const [errors, setErrors] = useState({})

  // Live validation on every change (useEffect, like Lab 3)
  useEffect(() => {
    const e = {}
    if (form.title.trim().length < 3) e.title = 'Title needs at least 3 characters'
    if (!form.description.trim()) e.description = 'Description is required'
    else if (form.description.length > 500) e.description = 'Max 500 characters'
    if (!/^\S+@\S+\.\S+$/.test(form.contact)) e.contact = 'Enter a valid email'
    setErrors(e)
  }, [form])

  const change = (k) => (ev) => setForm({ ...form, [k]: ev.target.value })
  const submit = async (ev) => {
    ev.preventDefault()
    await addItem(form)
    setForm(empty)
    onSaved()
  }

  return (
    <form className="card" onSubmit={submit}>
      <h3>Report an item</h3>
      <select value={form.type} onChange={change('type')}>
        <option value="LOST">I lost something</option>
        <option value="FOUND">I found something</option>
      </select>
      <input placeholder="Title" value={form.title} onChange={change('title')} />
      {errors.title && <small className="err">{errors.title}</small>}
      <textarea placeholder="Describe colour, brand, marks..." value={form.description} onChange={change('description')} />
      <small>{form.description.length}/500 characters (live state)</small>
      {errors.description && <small className="err">{errors.description}</small>}
      <select value={form.category} onChange={change('category')}>
        {['Bags', 'Electronics', 'Documents', 'Accessories', 'Others'].map((c) => <option key={c}>{c}</option>)}
      </select>
      <input placeholder="Location" value={form.location} onChange={change('location')} />
      <input placeholder="Contact email" value={form.contact} onChange={change('contact')} />
      {errors.contact && <small className="err">{errors.contact}</small>}
      <button disabled={Object.keys(errors).length > 0}>Submit</button>
    </form>
  )
}
