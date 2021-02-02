
package mage.cards.m;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AttackedThisTurnWatcher;
import mage.watchers.common.PermanentsEnteredBattlefieldWatcher;

/**
 *
 * @author jeffwadsworth
 */
public final class MadDog extends CardImpl {

    public MadDog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your end step, if Mad Dog didn't attack or come under your control this turn, sacrifice it.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(new BeginningOfEndStepTriggeredAbility(new SacrificeSourceEffect(), TargetController.YOU, false), MadDogCondition.instance, "At the beginning of your end step, if {this} didn't attack or come under your control this turn, sacrifice it");
        ability.addWatcher(new AttackedThisTurnWatcher());
        ability.addWatcher(new PermanentsEnteredBattlefieldWatcher());
        this.addAbility(ability);

    }

    private MadDog(final MadDog card) {
        super(card);
    }

    @Override
    public MadDog copy() {
        return new MadDog(this);
    }
}

enum MadDogCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent madDog = game.getPermanent(source.getSourceId());
        PermanentsEnteredBattlefieldWatcher watcher = game.getState().getWatcher(PermanentsEnteredBattlefieldWatcher.class);
        AttackedThisTurnWatcher watcher2 = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        if (watcher != null
                && watcher2 != null
                && madDog != null) {
            // For some reason, compare did not work when checking the lists.  Thus the interation.
            List<Permanent> permanents = watcher.getThisTurnEnteringPermanents(source.getControllerId());
            if (permanents.stream().anyMatch((p) -> (p.getId().equals(madDog.getId())))) {
                return false;
            }
            Set<MageObjectReference> mor = watcher2.getAttackedThisTurnCreatures();
            if (mor.stream().anyMatch((m) -> (m.getPermanent(game).equals(madDog)))) {
                return false;
            }
            return true; // Mad Dog did not come into play this turn nor did he attack this turn.  Sacrifice the hound.
        }
        return false;
    }
}
