package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Reincarnation extends CardImpl {

    public Reincarnation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}{G}");

        // Choose target creature. When that creature dies this turn, return a creature card
        // from its owner's graveyard to the battlefield under the control of that creature's owner.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new WhenTargetDiesDelayedTriggeredAbility(new ReincarnationEffect(), SetTargetPointer.CARD),
                true,
                "Choose target creature. "
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Reincarnation(final Reincarnation card) {
        super(card);
    }

    @Override
    public Reincarnation copy() {
        return new Reincarnation(this);
    }
}

class ReincarnationEffect extends OneShotEffect {

    public ReincarnationEffect() {
        super(Outcome.Detriment);
        staticText = "return a creature card from its owner's graveyard to the battlefield under the control of that creature's owner";
    }

    public ReincarnationEffect(final ReincarnationEffect effect) {
        super(effect);
    }

    @Override
    public ReincarnationEffect copy() {
        return new ReincarnationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player owner = game.getPlayer(game.getOwnerId(getTargetPointer().getFirst(game, source)));
        if (controller == null || owner == null) {
            return false;
        }
        FilterCreatureCard filter = new FilterCreatureCard("a creature card from " + owner.getName() + "'s graveyard");
        filter.add(new OwnerIdPredicate(owner.getId()));
        TargetCardInGraveyard target = new TargetCardInGraveyard(filter);
        target.setNotTarget(true);
        if (target.canChoose(controller.getId(), source, game)
                && controller.chooseTarget(outcome, target, source, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, true, null);
            }
        }
        return true;
    }
}
