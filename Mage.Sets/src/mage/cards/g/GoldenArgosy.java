package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.watchers.common.CrewedVehicleWatcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class GoldenArgosy extends CardImpl {

    public GoldenArgosy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Whenever Golden Argosy attacks, exile each creature that crewed it this turn. Return them to the battlefield tapped under their owner's control at the beginning of the next end step.
        this.addAbility(new AttacksTriggeredAbility(new GoldenArgosyEffect()), new CrewedVehicleWatcher());

        // Crew 1
        this.addAbility(new CrewAbility(1));
    }

    private GoldenArgosy(final GoldenArgosy card) {
        super(card);
    }

    @Override
    public GoldenArgosy copy() {
        return new GoldenArgosy(this);
    }
}

class GoldenArgosyEffect extends OneShotEffect {

    GoldenArgosyEffect() {
        super(Outcome.Benefit);
        staticText = "exile each creature that crewed it this turn. Return them to the battlefield " +
                "tapped under their owner's control at the beginning of the next end step";
    }

    private GoldenArgosyEffect(final GoldenArgosyEffect effect) {
        super(effect);
    }

    @Override
    public GoldenArgosyEffect copy() {
        return new GoldenArgosyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent vehicle = game.getPermanent(source.getSourceId());
        if (player == null || vehicle == null) {
            return false;
        }
        Cards cards = new CardsImpl(CrewedVehicleWatcher.getCrewers(vehicle, game));
        if (cards.isEmpty()) {
            return false;
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.retainZone(Zone.EXILED, game);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new ReturnToBattlefieldUnderOwnerControlTargetEffect(true, false)
                        .setTargetPointer(new FixedTargets(cards, game))
                        .setText("return them to the battlefield tapped")
        ), source);
        return true;
    }
}
