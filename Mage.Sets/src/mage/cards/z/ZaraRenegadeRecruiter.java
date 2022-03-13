package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPlayerOrPlaneswalker;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetPlayerOrPlaneswalker;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZaraRenegadeRecruiter extends CardImpl {

    public ZaraRenegadeRecruiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Zara, Renegade Recruiter attacks, look at defending player's hand. You may put a creature card from it onto the battlefield under your control tapped and attacking that player or a planeswalker they control. Return that creature to its owner's hand at the beginning of the next end step.
        this.addAbility(new AttacksTriggeredAbility(
                new ZaraRenegadeRecruiterEffect(), false, null, SetTargetPointer.PLAYER
        ));
    }

    private ZaraRenegadeRecruiter(final ZaraRenegadeRecruiter card) {
        super(card);
    }

    @Override
    public ZaraRenegadeRecruiter copy() {
        return new ZaraRenegadeRecruiter(this);
    }
}

class ZaraRenegadeRecruiterEffect extends OneShotEffect {

    ZaraRenegadeRecruiterEffect() {
        super(Outcome.Benefit);
        staticText = "look at defending player's hand. You may put a creature card from it onto the battlefield " +
                "under your control tapped and attacking that player or a planeswalker they control. " +
                "Return that creature to its owner's hand at the beginning of the next end step";
    }

    private ZaraRenegadeRecruiterEffect(final ZaraRenegadeRecruiterEffect effect) {
        super(effect);
    }

    @Override
    public ZaraRenegadeRecruiterEffect copy() {
        return new ZaraRenegadeRecruiterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller == null || player == null || player.getHand().isEmpty()) {
            return false;
        }
        TargetCardInHand targetCard = new TargetCardInHand(
                0, 1, StaticFilters.FILTER_CARD_CREATURE
        );
        controller.choose(outcome, player.getHand(), targetCard, game);
        Card card = game.getCard(targetCard.getFirstTarget());
        if (card == null) {
            return false;
        }
        controller.moveCards(
                card, Zone.BATTLEFIELD, source, game, true,
                false, false, null
        );
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return false;
        }
        UUID defenderId;
        if (game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER,
                player.getId(), source, game
        ) < 1) {
            defenderId = player.getId();
        } else {
            FilterPlayerOrPlaneswalker filter = new FilterPlayerOrPlaneswalker(
                    player.getName() + " or a planeswalker controlled by " + player.getName()
            );
            filter.getPlayerFilter().add(new PlayerIdPredicate(player.getId()));
            filter.getPermanentFilter().add(new ControllerIdPredicate(player.getId()));
            TargetPlayerOrPlaneswalker target = new TargetPlayerOrPlaneswalker(filter);
            target.setNotTarget(true);
            controller.choose(outcome, target, source, game);
            defenderId = target.getFirstTarget();
        }
        game.getCombat().addAttackerToCombat(permanent.getId(), defenderId, game);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new ReturnToHandTargetEffect().setTargetPointer(new FixedTarget(permanent, game))
        ), source);
        return true;
    }
}
