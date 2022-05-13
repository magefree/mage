package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IndustrialAdvancement extends CardImpl {

    public IndustrialAdvancement(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // At the beginning of your end step, you may sacrifice a creature. If you do, look at the top X cards of your library, when X is that creature's mana value. You may put a creature card from among them onto the battlefield. Put the rest on the bottom of your library in a random order.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new IndustrialAdvancementEffect(), TargetController.YOU, false
        ));
    }

    private IndustrialAdvancement(final IndustrialAdvancement card) {
        super(card);
    }

    @Override
    public IndustrialAdvancement copy() {
        return new IndustrialAdvancement(this);
    }
}

class IndustrialAdvancementEffect extends OneShotEffect {

    IndustrialAdvancementEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice a creature. If you do, look at the top X cards of your library, " +
                "where X is that creature's mana value. You may put a creature card from among them " +
                "onto the battlefield. Put the rest on the bottom of your library in a random order";
    }

    private IndustrialAdvancementEffect(final IndustrialAdvancementEffect effect) {
        super(effect);
    }

    @Override
    public IndustrialAdvancementEffect copy() {
        return new IndustrialAdvancementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetControlledCreaturePermanent(0, 1);
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null || !permanent.sacrifice(source, game)) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, permanent.getManaValue()));
        if (cards.isEmpty()) {
            return true;
        }
        TargetCard targetCard = new TargetCardInLibrary(
                0, 1, StaticFilters.FILTER_CARD_CREATURE
        );
        player.choose(outcome, cards, targetCard, game);
        player.moveCards(game.getCard(targetCard.getFirstTarget()), Zone.BATTLEFIELD, source, game);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
