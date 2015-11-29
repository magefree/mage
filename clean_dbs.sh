#!/usr/bin/env bash
# Cleans the DB from Server, Client and Test modules
find . -type f -iname 'cards.h2*.db' -print -delete
