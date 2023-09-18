
package mage.cards.s;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class Smokebraider extends CardImpl {

    public Smokebraider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Add two mana in any combination of colors. Spend this mana only to cast Elemental spells or activate abilities of Elementals.
        this.addAbility(new ConditionalAnyColorManaAbility(2, new SmokebraiderManaBuilder()));
    }

    private Smokebraider(final Smokebraider card) {
        super(card);
    }

    @Override
    public Smokebraider copy() {
        return new Smokebraider(this);
    }
}

class SmokebraiderManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new SmokebraiderConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast Elemental spells or activate abilities of Elementals";
    }
}

class SmokebraiderConditionalMana extends ConditionalMana {

    public SmokebraiderConditionalMana(Mana mana) {
        super(mana);
        this.staticText = "Spend this mana only to cast Elemental spells or activate abilities of Elementals";
        addCondition(new SmokebraiderManaCondition());
    }
}

class SmokebraiderManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        if (object != null && object.hasSubtype(SubType.ELEMENTAL, game)) {
            return true;
        }
        return false;
    }
}
