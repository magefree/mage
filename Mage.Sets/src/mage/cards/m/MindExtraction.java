package mage.cards.m;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MindExtraction extends CardImpl {

    public MindExtraction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // As an additional cost to cast this spell, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(
                1, 1, StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT, false
        )));

        // Target player reveals their hand and discards all cards of each of the sacrificed creature’s colors.
        this.getSpellAbility().addEffect(new MindExtractionEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private MindExtraction(final MindExtraction card) {
        super(card);
    }

    @Override
    public MindExtraction copy() {
        return new MindExtraction(this);
    }
}

class MindExtractionEffect extends OneShotEffect {

    MindExtractionEffect() {
        super(Outcome.Benefit);
        staticText = "Target player reveals their hand and discards all cards of each of the sacrificed creature’s colors.";
    }

    private MindExtractionEffect(final MindExtractionEffect effect) {
        super(effect);
    }

    @Override
    public MindExtractionEffect copy() {
        return new MindExtractionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller == null || player == null) {
            return false;
        }
        Permanent sacrificedCreature = null;
        for (Cost cost : source.getCosts()) {
            if (!(cost instanceof SacrificeTargetCost)) {
                continue;
            }
            SacrificeTargetCost sacCost = (SacrificeTargetCost) cost;
            for (Permanent permanent : sacCost.getPermanents()) {
                sacrificedCreature = permanent;
                break;
            }
        }
        if (sacrificedCreature == null) {
            return false;
        }
        ObjectColor color = sacrificedCreature.getColor(game);
        Cards cards = new CardsImpl(player.getHand());
        if (cards.isEmpty()) {
            return true;
        }
        player.revealCards(source, cards, game);
        if (color.isColorless()) {
            return true;
        }
        Cards toDiscard = new CardsImpl();
        cards.getCards(game)
                .stream()
                .filter(card -> card.getColor(game).shares(color))
                .forEach(toDiscard::add);
        toDiscard.getCards(game)
                .stream()
                .forEach(card -> player.discard(card, source, game));
        return true;
    }
}