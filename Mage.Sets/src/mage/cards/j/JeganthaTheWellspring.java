package mage.cards.j;

import mage.ConditionalMana;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.CompanionAbility;
import mage.abilities.keyword.CompanionCondition;
import mage.abilities.mana.BasicManaAbility;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SubType;
import mage.constants.SuperType;
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
        this.addAbility(new JeganthaTheWellspringManaAbility());
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
    public boolean isLegal(Set<Card> deck) {
        return deck.stream().allMatch(JeganthaTheWellspringCompanionCondition::checkCard);
    }

    private static final boolean checkCard(Card card) {
        Map<String, Integer> symbolMap = new HashMap();
        return card.getManaCost()
                .getSymbols()
                .stream()
                .anyMatch(s -> symbolMap.compute(
                        s, (str, i) -> (i == null) ? 1 : i + 1
                ) > 1);
    }
}

class JeganthaTheWellspringManaAbility extends BasicManaAbility {

    JeganthaTheWellspringManaAbility() {
        super(makeManaEffect());
        this.addEffect(new BasicManaEffect(
                new JeganthaTheWellspringConditionalMana("U")
        ).setText("{U}"));
        this.addEffect(new BasicManaEffect(
                new JeganthaTheWellspringConditionalMana("B")
        ).setText("{B}"));
        this.addEffect(new BasicManaEffect(
                new JeganthaTheWellspringConditionalMana("R")
        ).setText("{R}"));
        this.addEffect(new BasicManaEffect(
                new JeganthaTheWellspringConditionalMana("G")
        ).setText("{G}. This mana can't be spent to pay generic mana costs."));
        this.netMana.add(Mana.WhiteMana(1));
        this.netMana.add(Mana.BlueMana(1));
        this.netMana.add(Mana.BlackMana(1));
        this.netMana.add(Mana.RedMana(1));
        this.netMana.add(Mana.GreenMana(1));
    }

    private JeganthaTheWellspringManaAbility(JeganthaTheWellspringManaAbility ability) {
        super(ability);
    }

    @Override
    public JeganthaTheWellspringManaAbility copy() {
        return new JeganthaTheWellspringManaAbility(this);
    }

    private static final ManaEffect makeManaEffect() {
        return (ManaEffect) new BasicManaEffect(
                new JeganthaTheWellspringConditionalMana("W")
        ).setText("{W}");
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
        this.manaSymbol = manaSymbol.toLowerCase();
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
        ).map(String::toLowerCase).anyMatch(s -> s.contains(manaSymbol));
    }
}
