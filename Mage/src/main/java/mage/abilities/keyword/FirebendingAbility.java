package mage.abilities.keyword;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.Collections;

/**
 * @author TheElk801
 */
public class FirebendingAbility extends AttacksTriggeredAbility {

    private final DynamicValue amount;

    public FirebendingAbility(int amount) {
        this(StaticValue.get(amount));
    }

    public FirebendingAbility(DynamicValue amount) {
        super(new FirebendingAbilityEffect(amount));
        this.amount = amount;
    }

    private FirebendingAbility(final FirebendingAbility ability) {
        super(ability);
        this.amount = ability.amount;
    }

    @Override
    public FirebendingAbility copy() {
        return new FirebendingAbility(this);
    }

    @Override
    public String getRule() {
        if (amount instanceof StaticValue) {
            return "firebending " + amount +
                    " <i>(Whenever this creature attacks, add " +
                    String.join("", Collections.nCopies(((StaticValue) amount).getValue(), "{R}")) +
                    ". This mana lasts until end of combat.)</i>";
        }
        return "firebending X, where X is " + amount.getMessage() +
                ". <i>(Whenever this creature attacks, add X {R}. This mana lasts until end of combat.)</i>";
    }
}

class FirebendingAbilityEffect extends OneShotEffect {

    private final DynamicValue amount;

    FirebendingAbilityEffect(DynamicValue amount) {
        super(Outcome.Benefit);
        this.amount = amount;
    }

    private FirebendingAbilityEffect(final FirebendingAbilityEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public FirebendingAbilityEffect copy() {
        return new FirebendingAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int amount = Math.max(this.amount.calculate(game, source, this), 0);
        if (amount > 0) {
            player.getManaPool().addMana(Mana.RedMana(amount), game, source, Duration.EndOfCombat);
        }
        game.fireEvent(GameEvent.getEvent(
                GameEvent.EventType.FIREBENDED, source.getSourceId(),
                source, source.getControllerId(), amount
        ));
        return true;
    }
}
