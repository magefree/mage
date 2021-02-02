
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author LoneFox
 *
 */
public final class ErgRaiders extends CardImpl {

    public ErgRaiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, if Erg Raiders didn't attack this turn, Erg Raiders deals 2 damage to you unless it came under your control this turn.
        TriggeredAbility ability = new ConditionalInterveningIfTriggeredAbility(new BeginningOfEndStepTriggeredAbility(new DamageControllerEffect(2), TargetController.YOU, false),
                new ErgRaidersCondition(), "At the beginning of your end step, if {this} didn't attack this turn, {this} deals 2 damage to you unless it came under your control this turn.");
        ability.addWatcher(new AttackedThisTurnWatcher());
        this.addAbility(ability);
    }

    private ErgRaiders(final ErgRaiders card) {
        super(card);
    }

    @Override
    public ErgRaiders copy() {
        return new ErgRaiders(this);
    }
}

class ErgRaidersCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent raiders = game.getPermanentOrLKIBattlefield(source.getSourceId());
        AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        // wasControlledFromStartOfControllerTurn should be checked during resolution I guess, but shouldn't be relevant
        return raiders != null &&raiders.wasControlledFromStartOfControllerTurn() && watcher != null && !watcher.getAttackedThisTurnCreatures().contains(new MageObjectReference(raiders, game));
    }
}
