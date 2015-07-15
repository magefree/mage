#!/usr/bin/env bash
# Cleans the DB from Server, Client and Test modules
find . -type f | grep -i cards.h2*.db | xargs rm -v