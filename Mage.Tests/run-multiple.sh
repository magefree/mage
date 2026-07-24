#!/bin/bash

# Скрипт для запуска теста 10 раз и сбора статистики
# Сохраните как run_test_10_times.sh и выполните chmod +x run_test_10_times.sh

TEST_CLASS="org.mage.test.cards.cost.modaldoublefaced.ModalDoubleFacedCardsTest"
TEST_METHOD="test_Single_Moonmist_MustTransformAllDFC"
SUCCESS=0
FAIL=0

echo "====================================="
echo "Запуск теста $TEST_METHOD 10 раз"
echo "====================================="
echo ""

for i in {1..10}; do
    echo "--- Запуск #$i ---"
    
    # Запускаем тест и сохраняем вывод
    mvn test -Dtest=$TEST_CLASS#$TEST_METHOD > test_output_$i.log 2>&1
    
    # Проверяем результат
    if [ $? -eq 0 ]; then
        echo "✅ Запуск #$i: УСПЕШНО"
        ((SUCCESS++))
    else
        echo "❌ Запуск #$i: ПРОВАЛ"
        ((FAIL++))
    fi
    
    echo ""
done

echo "====================================="
echo "ИТОГОВАЯ СТАТИСТИКА"
echo "====================================="
echo "✅ Успешно: $SUCCESS"
echo "❌ Провалов: $FAIL"
echo "📊 Успешность: $(( (SUCCESS * 100) / 10 ))%"
echo "====================================="

# Очищаем временные файлы (опционально)
# rm -f test_output_*.log
