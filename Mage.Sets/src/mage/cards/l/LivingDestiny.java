package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class LivingDestiny extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("a creature card from your hand");

    public LivingDestiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // As an additional cost to cast Living Destiny, reveal a creature card from your hand.
        TargetCardInHand targetCard = new TargetCardInHand(filter);
        this.getSpellAbility().addCost(new RevealTargetFromHandCost(targetCard));

        // You gain life equal to the revealed card's converted mana cost.
        this.getSpellAbility().addEffect(new LivingDestinyEffect());
    }

    private LivingDestiny(final LivingDestiny card) {
        super(card);
    }

    @Override
    public LivingDestiny copy() {
        return new LivingDestiny(this);
    }
}

class LivingDestinyEffect extends OneShotEffect {

    public LivingDestinyEffect() {
        super(Outcome.GainLife);
        staticText = "You gain life equal to the revealed card's mana value";
    }

    public LivingDestinyEffect(LivingDestinyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        RevealTargetFromHandCost cost = (RevealTargetFromHandCost) source.getCosts().get(0);
        if (cost != null) {
            Player player = game.getPlayer(source.getControllerId());
            int CMC = cost.manaValues;
            if (player != null) {
                player.gainLife(CMC, game, source);
            }
        }
        return true;
    }

    @Override
    public LivingDestinyEffect copy() {
        return new LivingDestinyEffect(this);
    }
}