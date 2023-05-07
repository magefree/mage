package mage.game.permanent.token;

import mage.ConditionalMana;
import mage.MageInt;
import mage.Mana;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.PlaneswalkerCastManaCondition;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class CommodoreGuffToken extends TokenImpl {

    public CommodoreGuffToken() {
        super("Wizard Token", "1/1 red Wizard creature token with \"{T}: Add {R}. Spend this mana only to cast a planeswalker spell.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.WIZARD);
        color.setRed(true);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new ConditionalColoredManaAbility(Mana.RedMana(1), new CommodoreGuffTokenManaBuilder()));
    }

    private CommodoreGuffToken(final CommodoreGuffToken token) {
        super(token);
    }

    public CommodoreGuffToken copy() {
        return new CommodoreGuffToken(this);
    }
}

class CommodoreGuffTokenManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new CommodoreGuffTokenConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a planeswalker spell.";
    }
}

class CommodoreGuffTokenConditionalMana extends ConditionalMana {

    CommodoreGuffTokenConditionalMana(Mana mana) {
        super(mana);
        addCondition(new PlaneswalkerCastManaCondition());
    }
}
