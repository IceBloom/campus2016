#!/bin/bash
#ͳ�Ʒ��ʴ�������10��ip
cat access.log.txt | awk '{print $1}' | uniq -c | sort -rn | head -10 > result.txt
