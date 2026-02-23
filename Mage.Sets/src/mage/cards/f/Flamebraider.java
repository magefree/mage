package mage.cards.f;

import java.util.UUID;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.constants.SubType;
import mage.game.Game;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class Flamebraider extends CardImpl {

    public Flamebraider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Add two mana in any combination of colors. Spend this mana only to cast Elemental spells or activate abilities of Elemental sources.
        this.addAbility(new ConditionalAnyColorManaAbility(2, new FlamebraiderManaBuilder()));
    }

    private Flamebraider(final Flamebraider card) {
        super(card);
    }

    @Override
    public Flamebraider copy() {
        return new Flamebraider(this);
    }
}

class FlamebraiderManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new FlamebraiderConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast Elemental spells or activate abilities of Elemental sources";
    }
}

class FlamebraiderConditionalMana extends ConditionalMana {

    public FlamebraiderConditionalMana(Mana mana) {
        super(mana);
        this.staticText = "Spend this mana only to cast Elemental spells or activate abilities of Elemental sources";
        addCondition(FlamebraiderManaCondition.instance);
    }
}

enum FlamebraiderManaCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return object != null && object.hasSubtype(SubType.ELEMENTAL, game);
    }
}
