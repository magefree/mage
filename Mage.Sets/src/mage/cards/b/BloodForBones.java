package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;

/**
 * @author TheElk801
 */
public final class BloodForBones extends CardImpl {

    public BloodForBones(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // As an additional cost to cast this spell, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(
                new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)
        ));

        // Return a creature card from your graveyard to the battlefield, then return another creature card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new BloodForBonesEffect());
    }

    private BloodForBones(final BloodForBones card) {
        super(card);
    }

    @Override
    public BloodForBones copy() {
        return new BloodForBones(this);
    }
}

class BloodForBonesEffect extends OneShotEffect {

    private static final FilterCard filter
            = new FilterCreatureCard("creature card in your graveyard (to put onto the battlefield");
    private static final FilterCard filter2
            = new FilterCreatureCard("creature card in your graveyard (to put into your hand");

    BloodForBonesEffect() {
        super(Outcome.Benefit);
        staticText = "Return a creature card from your graveyard to the battlefield, " +
                "then return another creature card from your graveyard to your hand.";
    }

    private BloodForBonesEffect(final BloodForBonesEffect effect) {
        super(effect);
    }

    @Override
    public BloodForBonesEffect copy() {
        return new BloodForBonesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getGraveyard().getCards(game).stream().noneMatch(Card::isCreature)) {
            return false;
        }
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(filter);
        if (player.choose(outcome, player.getGraveyard(), target, game)) {
            player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
        }
        if (player.getGraveyard().getCards(game).stream().noneMatch(Card::isCreature)) {
            return true;
        }
        target = new TargetCardInYourGraveyard(filter2);
        if (player.choose(outcome, player.getGraveyard(), target, game)) {
            player.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
        }
        return true;
    }
}