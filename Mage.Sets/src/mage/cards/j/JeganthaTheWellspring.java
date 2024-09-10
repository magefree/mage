package mage.cards.j;

import mage.ConditionalMana;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.keyword.CompanionAbility;
import mage.abilities.keyword.CompanionCondition;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JeganthaTheWellspring extends CardImpl {

    public JeganthaTheWellspring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R/G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.ELK);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Companion — No card in your starting deck has more than one of the same mana symbol in its mana cost.
        this.addAbility(new CompanionAbility(JeganthaTheWellspringCompanionCondition.instance));

        // {T}: Add {W}{U}{B}{R}{G}. This mana can't be spent to pay generic mana costs.
        this.addAbility(new ConditionalColoredManaAbility(
                new TapSourceCost(), new Mana(1, 1, 1, 1, 1, 0, 0, 0), new JeganthaManaBuilder()
        ));
    }

    private JeganthaTheWellspring(final JeganthaTheWellspring card) {
        super(card);
    }

    @Override
    public JeganthaTheWellspring copy() {
        return new JeganthaTheWellspring(this);
    }
}

enum JeganthaTheWellspringCompanionCondition implements CompanionCondition {
    instance;

    @Override
    public String getRule() {
        return "No card in your starting deck has more than one of the same mana symbol in its mana cost.";
    }

    @Override
    public boolean isLegal(Set<Card> deck, int minimumDeckSize) {
        return deck.stream().noneMatch(JeganthaTheWellspringCompanionCondition::checkCard);
    }

    private static boolean checkCard(Card card) {
        Map<String, Integer> symbolMap = new HashMap();
        return card.getManaCostSymbols()
                .stream()
                .anyMatch(s -> symbolMap.compute(
                s, (str, i) -> (i == null) ? 1 : i + 1
        ) > 1);
    }
}

class JeganthaManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new JeganthaConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "This mana can't be spent to pay generic mana costs";
    }
}

class JeganthaConditionalMana extends ConditionalMana {

    JeganthaConditionalMana(Mana mana) {
        super(mana);
        staticText = "This mana can't be spent to pay generic mana costs";
        addCondition(new JeganthaManaCondition());
    }
}

class JeganthaManaCondition extends ManaCondition {

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        // TODO find a better method.  this one forces the user to pay off the generic mana before continuing.
        return source.getManaCostsToPay().getUnpaid().getMana().getGeneric() == 0;
    }
}
