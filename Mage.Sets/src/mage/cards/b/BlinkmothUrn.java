package mage.cards.b;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfFirstMainTriggeredAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author nickmyers
 */
public final class BlinkmothUrn extends CardImpl {

    public BlinkmothUrn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // At the beginning of each player's precombat main phase, if Blinkmoth Urn is untapped, that player adds {C} for each artifact they control.
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(new BlinkmothUrnEffect(), TargetController.EACH_PLAYER, false)
                        .withInterveningIf(SourceTappedCondition.UNTAPPED));
    }

    private BlinkmothUrn(final BlinkmothUrn card) {
        super(card);
    }

    @Override
    public BlinkmothUrn copy() {
        return new BlinkmothUrn(this);
    }

}

class BlinkmothUrnEffect extends OneShotEffect {

    BlinkmothUrnEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "that player adds {C} for each artifact they control";
    }

    private BlinkmothUrnEffect(final BlinkmothUrnEffect effect) {
        super(effect);
    }

    @Override
    public BlinkmothUrnEffect copy() {
        return new BlinkmothUrnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        FilterArtifactPermanent filter = new FilterArtifactPermanent("artifacts you control");
        filter.add(new ControllerIdPredicate(game.getActivePlayerId()));
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (player != null && sourcePermanent != null) {
            player.getManaPool().addMana(Mana.ColorlessMana(
                    game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game).
                    size()), game, source, false);
            return true;
        }
        return false;
    }
}
