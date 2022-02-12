
package mage.cards.l;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.BecomesBlackZombieAdditionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author JRHerlehy
 */
public final class LilianaDeathsMajesty extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Zombie creatures");

    static {
        filter.add(Predicates.not(SubType.ZOMBIE.getPredicate()));
    }

    public LilianaDeathsMajesty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.LILIANA);

        //Starting Loyalty: 5
        this.setStartingLoyalty(5);

        // +1: Create a 2/2 black Zombie creature token. Put the top two cards of your library into your graveyard.
        LoyaltyAbility ability = new LoyaltyAbility(new CreateTokenEffect(new ZombieToken()), 1);
        ability.addEffect(new MillCardsControllerEffect(2));
        this.addAbility(ability);

        // -3: Return target creature card from your graveyard to the battlefield. That creature is a black Zombie in addition to its other colors and types.
        ability = new LoyaltyAbility(new BecomesBlackZombieAdditionEffect() // because the effect has to be active for triggered effects that e.g. check if the creature entering is a Zombie, the continuous effect needs to be added before the card moving effect is applied
                .setText(""), -3);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        ability.addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("Return target creature card from your graveyard to the battlefield. That creature is a black Zombie in addition to its other colors and types"));
        this.addAbility(ability);

        // -7: Destroy all non-Zombie creatures.
        ability = new LoyaltyAbility(new DestroyAllEffect(filter), -7);
        this.addAbility(ability);
    }

    private LilianaDeathsMajesty(final LilianaDeathsMajesty card) {
        super(card);
    }

    @Override
    public LilianaDeathsMajesty copy() {
        return new LilianaDeathsMajesty(this);
    }
}
