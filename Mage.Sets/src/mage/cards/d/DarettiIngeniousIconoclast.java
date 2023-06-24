package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.DarettiConstructToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInGraveyardBattlefieldOrStack;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DarettiIngeniousIconoclast extends CardImpl {

    public DarettiIngeniousIconoclast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DARETTI);

        this.setStartingLoyalty(3);

        // +1: Create a 1/1 colorless Construct artifact creature token with defender.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new DarettiConstructToken()), 1));

        // -1: You may sacrifice an artifact. If you do, destroy target artifact or creature.
        Ability ability = new LoyaltyAbility(
                new DoIfCostPaid(
                        new DestroyTargetEffect(),
                        new SacrificeTargetCost(new TargetControlledPermanent(
                                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN
                        ))
                ), -1
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE).withChooseHint("to destroy"));
        this.addAbility(ability);

        // -6: Choose target artifact card in a graveyard or artifact on the battlefield. Create three tokens that are copies of it.
        ability = new LoyaltyAbility(
                new CreateTokenCopyTargetEffect(null, null, false, 3)
                        .setText("Choose target artifact card in a graveyard or artifact on the battlefield. " +
                                "Create three tokens that are copies of it"), -6
        );
        ability.addTarget(new TargetCardInGraveyardBattlefieldOrStack(1, 1,
                StaticFilters.FILTER_CARD_ARTIFACT, StaticFilters.FILTER_PERMANENT_ARTIFACT));
        this.addAbility(ability);
    }

    private DarettiIngeniousIconoclast(final DarettiIngeniousIconoclast card) {
        super(card);
    }

    @Override
    public DarettiIngeniousIconoclast copy() {
        return new DarettiIngeniousIconoclast(this);
    }
}
