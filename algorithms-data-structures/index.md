---
title: Algorithm & Data Structures Artifact Enhancement
nav_order: 2
---

# Algorithm & Data Structures Artifact Enhancement

{% for file in site.static_files %}
{% if file.path contains '/algorithms-data-structures/' %}
- [{{ file.name }}]({{ file.path }})
{% endif %}
{% endfor %}
