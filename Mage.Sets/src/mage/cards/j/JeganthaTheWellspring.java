package mage.cards.j;

import mage.ConditionalMana;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.keyword.CompanionAbility;
import mage.abilities.keyword.CompanionCondition;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;

import java.util.*;

/**
 * @author TheElk801
 */
public final class JeganthaTheWellspring extends CardImpl {

    public JeganthaTheWellspring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R/G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.ELK);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Companion — No card in your starting deck has more than one of the same mana symbol in its mana cost.
        this.addAbility(new CompanionAbility(JeganthaTheWellspringCompanionCondition.instance));

        // {T}: Add {W}{U}{B}{R}{G}. This mana can't be spent to pay generic mana costs.
        this.addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD, new JeganthaTheWellspringManaEffect(), new TapSourceCost()
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
    public boolean isLegal(Set<Card> deck, int startingHandSize) {
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

class JeganthaTheWellspringManaEffect extends ManaEffect {

    JeganthaTheWellspringManaEffect() {
        super();
        staticText = "Add {W}{U}{B}{R}{G}. This mana can't be spent to pay generic mana costs.";
    }

    private JeganthaTheWellspringManaEffect(final JeganthaTheWellspringManaEffect effect) {
        super(effect);
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        mana.add(new JeganthaTheWellspringConditionalMana("W"));
        mana.add(new JeganthaTheWellspringConditionalMana("U"));
        mana.add(new JeganthaTheWellspringConditionalMana("B"));
        mana.add(new JeganthaTheWellspringConditionalMana("R"));
        mana.add(new JeganthaTheWellspringConditionalMana("G"));
        return mana;
    }

    @Override
    public JeganthaTheWellspringManaEffect copy() {
        return new JeganthaTheWellspringManaEffect(this);
    }
}

class JeganthaTheWellspringConditionalMana extends ConditionalMana {

    JeganthaTheWellspringConditionalMana(String manaSymbol) {
        super(new Mana(ColoredManaSymbol.valueOf(manaSymbol)));
        addCondition(new JeganthaTheWellspringManaCondition(manaSymbol));
    }
}

class JeganthaTheWellspringManaCondition extends ManaCondition {

    private final String manaSymbol;

    JeganthaTheWellspringManaCondition(String manaSymbol) {
        this.manaSymbol = manaSymbol.toLowerCase(Locale.ENGLISH);
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        if (!(costToPay instanceof ManaCosts)) {
            return false;
        }
        return Arrays.stream(
                ((ManaCosts<ManaCost>) costToPay)
                        .getUnpaid()
                        .getText()
                        .split("[\\}\\{]")
        ).map(s -> s.toLowerCase(Locale.ENGLISH)).anyMatch(s -> s.contains(manaSymbol));
    }
}
