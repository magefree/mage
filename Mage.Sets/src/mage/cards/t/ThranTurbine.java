package mage.cards.t;

import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.AddConditionalColorlessManaEffect;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author spjspjs
 */
public final class ThranTurbine extends CardImpl {

    public ThranTurbine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // At the beginning of your upkeep, you may add {C} or {C}{C}. 
        // You can't spend this mana to cast spells.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                new ThranTurbineEffect(), TargetController.YOU, true));
    }

    private ThranTurbine(final ThranTurbine card) {
        super(card);
    }

    @Override
    public ThranTurbine copy() {
        return new ThranTurbine(this);
    }
}

class ThranTurbineEffect extends OneShotEffect {

    public ThranTurbineEffect() {
        super(Outcome.Benefit);
        staticText = "add {C}{C}. You can't spend this mana to cast spells";
    }

    private ThranTurbineEffect(final ThranTurbineEffect effect) {
        super(effect);
    }

    @Override
    public ThranTurbineEffect copy() {
        return new ThranTurbineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));

        if (player != null) {
            new AddConditionalColorlessManaEffect(2, new ThranTurbineManaBuilder()).apply(game, source);
            return true;
        }
        return false;
    }
}

class ThranTurbineManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new ThranTurbineConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "You can't spend this mana to cast spells";
    }
}

class ThranTurbineConditionalMana extends ConditionalMana {

    public ThranTurbineConditionalMana(Mana mana) {
        super(mana);
        staticText = "You can't spend this mana to cast spells";
        addCondition(new ThranTurbineManaCondition());
    }
}

class ThranTurbineManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        return !(source instanceof SpellAbility && !source.isActivated());
    }
}
