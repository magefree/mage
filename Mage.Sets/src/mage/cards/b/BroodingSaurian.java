package mage.cards.b;

import java.util.Iterator;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class BroodingSaurian extends CardImpl {

    public BroodingSaurian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.LIZARD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of each end step, each player gains control of all nontoken permanents they own.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new BroodingSaurianControlEffect(), TargetController.ANY, false));
    }

    private BroodingSaurian(final BroodingSaurian card) {
        super(card);
    }

    @Override
    public BroodingSaurian copy() {
        return new BroodingSaurian(this);
    }
}

class BroodingSaurianControlEffect extends ContinuousEffectImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public BroodingSaurianControlEffect() {
        super(Duration.EndOfGame, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.staticText = "each player gains control of all nontoken permanents they own";
    }

    public BroodingSaurianControlEffect(final BroodingSaurianControlEffect effect) {
        super(effect);
    }

    @Override
    public BroodingSaurianControlEffect copy() {
        return new BroodingSaurianControlEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        // add all creatures in range
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                FilterPermanent playerFilter = filter.copy();
                playerFilter.add(new OwnerIdPredicate(playerId));
                for (Permanent permanent : game.getBattlefield().getActivePermanents(playerFilter, playerId, game)) {
                    affectedObjectList.add(new MageObjectReference(permanent, game));
                }
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
            Permanent creature = it.next().getPermanent(game);
            if (creature != null) {
                if (!creature.isControlledBy(creature.getOwnerId())) {
                    creature.changeControllerId(creature.getOwnerId(), game, source);
                }
            } else {
                it.remove();
            }
        }
        if (affectedObjectList.isEmpty()) {
            this.discard();
        }
        return true;
    }
}
