package mage.cards.v;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VolsheTideturner extends CardImpl {

    public VolsheTideturner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Add {U}. Spend this mana only to cast an instant or sorcery spell or a kicked spell.
        this.addAbility(new ConditionalColoredManaAbility(
                new TapSourceCost(), Mana.BlueMana(1), new VolsheTideturnerManaBuilder()
        ));
    }

    private VolsheTideturner(final VolsheTideturner card) {
        super(card);
    }

    @Override
    public VolsheTideturner copy() {
        return new VolsheTideturner(this);
    }
}

class VolsheTideturnerManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new VolsheTideturnerConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast an instant or sorcery spell or a kicked spell.";
    }
}

class VolsheTideturnerConditionalMana extends ConditionalMana {

    public VolsheTideturnerConditionalMana(Mana mana) {
        super(mana);
        addCondition(new VolsheTideturnerCondition());
    }
}

class VolsheTideturnerCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (KickedCondition.ONCE.apply(game, source)) {
            return true;
        }
        if (!(source instanceof SpellAbility)) {
            return false;
        }
        MageObject object = game.getObject(source);
        return object != null && object.isInstantOrSorcery(game);
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return apply(game, source);
    }
}
