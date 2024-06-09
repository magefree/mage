package mage.cards.v;

import mage.abilities.LoyaltyAbility;
import mage.abilities.common.DealsDamageToThisAllTriggeredAbility;
import mage.abilities.common.delayed.UntilYourNextTurnDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.AssassinToken;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * If an effect creates a copy of one of the Assassin creature tokens, the copy
 * will also have the triggered ability.
 * <p>
 * Each Assassin token's triggered ability will trigger whenever it deals combat
 * damage to any player, including you.
 *
 * @author LevelX2
 */
public final class VraskaTheUnseen extends CardImpl {

    public VraskaTheUnseen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VRASKA);

        this.setStartingLoyalty(5);

        // +1: Until your next turn, whenever a creature deals combat damage to Vraska the Unseen, destroy that creature.
        this.addAbility(new LoyaltyAbility(new CreateDelayedTriggeredAbilityEffect(
                new UntilYourNextTurnDelayedTriggeredAbility(
                        new DealsDamageToThisAllTriggeredAbility(
                                new DestroyTargetEffect().setText("destroy that creature"),
                                false, StaticFilters.FILTER_PERMANENT_CREATURE,
                                SetTargetPointer.PERMANENT, true
                        )
                )
        ), 1));

        // -3: Destroy target nonland permanent.
        LoyaltyAbility ability = new LoyaltyAbility(new DestroyTargetEffect(), -3);
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);

        // -7: Create three 1/1 black Assassin creature tokens with "Whenever this creature deals combat damage to a player, that player loses the game."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new AssassinToken(), 3), -7));
    }

    private VraskaTheUnseen(final VraskaTheUnseen card) {
        super(card);
    }

    @Override
    public VraskaTheUnseen copy() {
        return new VraskaTheUnseen(this);
    }
}