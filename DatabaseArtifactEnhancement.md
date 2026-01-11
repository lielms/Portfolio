---

layout: page
title: (WIP) Database Artifact Enhancement
---

{% for file in site.static_files %}
{% if file.path contains '/Database Artifact Enhancement/' %}
- [{{ file.name }}]({{ file.path }})
{% endif %}
{% endfor %}
