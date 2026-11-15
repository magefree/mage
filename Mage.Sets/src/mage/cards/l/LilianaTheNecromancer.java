package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotNonTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LilianaTheNecromancer extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature cards from graveyards");

    public LilianaTheNecromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LILIANA);
        this.setStartingLoyalty(4);

        // +1: Target player loses 2 life.
        Ability ability = new LoyaltyAbility(new LoseLifeTargetEffect(2), 1);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // −1: Return target creature card from your graveyard to your hand.
        ability = new LoyaltyAbility(new ReturnFromGraveyardToHandTargetEffect(), -1);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // −7: Destroy up to two target creatures. Put up to two creature cards from graveyards onto the battlefield under your control.
        ability = new LoyaltyAbility(new DestroyTargetEffect(), -7);
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        ability.addEffect(new OneShotNonTargetEffect(
                new ReturnFromGraveyardToBattlefieldTargetEffect()
                        .setText("Put up to two creature cards from graveyards onto the battlefield under your control"),
                new TargetCardInGraveyard(0, 2, filter)
        ));
        this.addAbility(ability);
    }

    private LilianaTheNecromancer(final LilianaTheNecromancer card) {
        super(card);
    }

    @Override
    public LilianaTheNecromancer copy() {
        return new LilianaTheNecromancer(this);
    }
}
