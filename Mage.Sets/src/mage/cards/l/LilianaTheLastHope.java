package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnCardChosenFromGraveyardEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.command.emblems.LilianaTheLastHopeEmblem;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class LilianaTheLastHope extends CardImpl {

    public LilianaTheLastHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LILIANA);

        this.setStartingLoyalty(3);

        // +1: Up to one target creature gets -2/-1 until your next turn.
        Effect effect = new BoostTargetEffect(-2, -1, Duration.UntilYourNextTurn);
        effect.setText("Up to one target creature gets -2/-1 until your next turn");
        Ability ability = new LoyaltyAbility(effect, 1);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // -2: Put the top two cards of your library into your graveyard, then you may return a creature card from your graveyard to your hand.
        ability = new LoyaltyAbility(new MillCardsControllerEffect(2), -2);
        ability.addEffect(new ReturnCardChosenFromGraveyardEffect(
                true, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD, PutCards.HAND
        ).concatBy(", then"));
        this.addAbility(ability);

        // -7: You get an emblem with "At the beginning of your end step, create X 2/2 black Zombie creature tokens,
        // where X is two plus the number of Zombies you control."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new LilianaTheLastHopeEmblem()), -7));
    }

    private LilianaTheLastHope(final LilianaTheLastHope card) {
        super(card);
    }

    @Override
    public LilianaTheLastHope copy() {
        return new LilianaTheLastHope(this);
    }
}
