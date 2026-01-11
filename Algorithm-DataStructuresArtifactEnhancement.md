---

layout: page
title: (WIP) Algorithm and Data Structures Artifact Enhancement
---

{% for file in site.static_files %}
  {% if file.path contains '/notes/' %}
- [{{ file.name }}]({{ file.path }})
  {% endif %}
{% endfor %}
