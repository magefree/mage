package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChargeOfTheForeverBeast extends CardImpl {

    public ChargeOfTheForeverBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // As an additional cost to cast this spell, reveal a creature card from your hand.
        this.getSpellAbility().addCost(new RevealTargetFromHandCost(
                new TargetCardInHand(StaticFilters.FILTER_CARD_CREATURE_YOUR_HAND)
        ));

        // Charge of the Forever-Beast deals damage to target creature or planeswalker equal to the revealed card's power.
        this.getSpellAbility().addEffect(new ChargeOfTheForeverBeastEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private ChargeOfTheForeverBeast(final ChargeOfTheForeverBeast card) {
        super(card);
    }

    @Override
    public ChargeOfTheForeverBeast copy() {
        return new ChargeOfTheForeverBeast(this);
    }
}

class ChargeOfTheForeverBeastEffect extends OneShotEffect {

    ChargeOfTheForeverBeastEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals damage to target creature or planeswalker equal to the revealed card's power";
    }

    private ChargeOfTheForeverBeastEffect(final ChargeOfTheForeverBeastEffect effect) {
        super(effect);
    }

    @Override
    public ChargeOfTheForeverBeastEffect copy() {
        return new ChargeOfTheForeverBeastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        RevealTargetFromHandCost cost = (RevealTargetFromHandCost) source.getCosts().get(0);
        if (permanent == null || cost == null) {
            return false;
        }
        Card card = cost.getRevealedCards().get(0);
        if (card == null) {
            return false;
        }
        return permanent.damage(card.getPower().getValue(), source.getSourceId(), source, game) > 0;
    }
}