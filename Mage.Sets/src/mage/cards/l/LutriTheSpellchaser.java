package mage.cards.l;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.keyword.CompanionAbility;
import mage.abilities.keyword.CompanionCondition;
import mage.abilities.keyword.FlashAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.target.TargetSpell;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LutriTheSpellchaser extends CardImpl {

    private static final FilterSpell filter
            = new FilterInstantOrSorcerySpell("instant or sorcery spell you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public LutriTheSpellchaser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U/R}{U/R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.OTTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Companion — Each nonland card in your starting deck has a different name.
        this.addAbility(new CompanionAbility(LutriTheSpellchaserCompanionCondition.instance));

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Lutri, the Spellchaser enters the battlefield, if you cast it, copy target instant or sorcery spell you control. You may choose new targets for the copy.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new CopyTargetSpellEffect(), false),
                CastFromEverywhereSourceCondition.instance, "When {this} enters the battlefield, " +
                "if you cast it, copy target instant or sorcery spell you control. " +
                "You may choose new targets for the copy."
        );
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
    }

    private LutriTheSpellchaser(final LutriTheSpellchaser card) {
        super(card);
    }

    @Override
    public LutriTheSpellchaser copy() {
        return new LutriTheSpellchaser(this);
    }
}

enum LutriTheSpellchaserCompanionCondition implements CompanionCondition {
    instance;

    @Override
    public String getRule() {
        return "Each nonland card in your starting deck has a different name.";
    }

    @Override
    public boolean isLegal(Set<Card> deck, int startingHandSize) {
        Map<String, Integer> cardMap = new HashMap<>();
        deck.stream()
                .filter(card -> !card.hasCardTypeForDeckbuilding(CardType.LAND))
                .map(MageObject::getName)
                .forEach(s -> {
                    cardMap.putIfAbsent(s, 0);
                    cardMap.compute(s, (str, i) -> i + 1);
                });
        return cardMap.values().stream().noneMatch(i -> i > 1);
    }
}
