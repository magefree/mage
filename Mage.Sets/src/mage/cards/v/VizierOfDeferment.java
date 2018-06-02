
package mage.cards.v;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.AttackedThisTurnWatcher;
import mage.watchers.common.BlockedThisTurnWatcher;

/**
 *
 * @author jeffwadsworth
 */
public final class VizierOfDeferment extends CardImpl {

    public VizierOfDeferment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Vizier of Deferment enters the battlefield, you may exile target creature if it attacked or blocked this turn. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new EntersBattlefieldTriggeredAbility(new VizierOfDefermentEffect(), true);
        ability.addWatcher(new AttackedThisTurnWatcher());
        ability.addWatcher(new BlockedThisTurnWatcher());
        this.addAbility(ability);

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof EntersBattlefieldTriggeredAbility) {
            List<PermanentIdPredicate> creaturesThatCanBeTargeted = new ArrayList<>();
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that attacked or blocked this turn.");
            AttackedThisTurnWatcher watcherAttacked = (AttackedThisTurnWatcher) game.getState().getWatchers().get(AttackedThisTurnWatcher.class.getSimpleName());
            BlockedThisTurnWatcher watcherBlocked = (BlockedThisTurnWatcher) game.getState().getWatchers().get(BlockedThisTurnWatcher.class.getSimpleName());
            if (watcherAttacked != null) {
                for (MageObjectReference mor : watcherAttacked.getAttackedThisTurnCreatures()) {
                    Permanent permanent = mor.getPermanent(game);
                    if (permanent != null) {
                        creaturesThatCanBeTargeted.add(new PermanentIdPredicate(permanent.getId()));
                    }
                }
                if (watcherBlocked != null) {
                    for (MageObjectReference mor : watcherBlocked.getBlockedThisTurnCreatures()) {
                        Permanent permanent = mor.getPermanent(game);
                        if (permanent != null) {
                            creaturesThatCanBeTargeted.add(new PermanentIdPredicate(permanent.getId()));
                        }
                    }
                }
                filter.add(Predicates.or(creaturesThatCanBeTargeted));
                ability.getTargets().clear();
                ability.addTarget(new TargetCreaturePermanent(filter));
            }
        }
    }

    public VizierOfDeferment(final VizierOfDeferment card) {
        super(card);
    }

    @Override
    public VizierOfDeferment copy() {
        return new VizierOfDeferment(this);
    }
}

class VizierOfDefermentEffect extends OneShotEffect {

    public VizierOfDefermentEffect() {
        super(Outcome.Detriment);
        staticText = "you may exile target creature if it attacked or blocked this turn. Return that card to the battlefield under its owner's control at the beginning of the next end step";
    }

    public VizierOfDefermentEffect(final VizierOfDefermentEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null
                && permanent != null
                && sourcePermanent != null) {
            if (controller.moveCardToExileWithInfo(permanent, source.getSourceId(), sourcePermanent.getIdName(), source.getSourceId(), game, Zone.BATTLEFIELD, true)) {
                Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect();
                effect.setText("Return that card to the battlefield under its owner's control at the beginning of the next end step");
                effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
                return true;
            }
        }
        return false;
    }

    @Override
    public VizierOfDefermentEffect copy() {
        return new VizierOfDefermentEffect(this);
    }

}
