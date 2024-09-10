
package mage.cards.s;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.command.emblems.SorinSolemnVisitorEmblem;
import mage.game.permanent.token.SorinSolemnVisitorVampireToken;

/**
 *
 * @author LevelX2
 */
public final class SorinSolemnVisitor extends CardImpl {

    public SorinSolemnVisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SORIN);

        this.setStartingLoyalty(4);

        // +1: Until your next turn, creatures you control get +1/+0 and gain lifelink.
        Effect effect = new BoostControlledEffect(1, 0, Duration.UntilYourNextTurn, StaticFilters.FILTER_PERMANENT_CREATURES);
        effect.setText("Until your next turn, creatures you control get +1/+0");
        LoyaltyAbility loyaltyAbility = new LoyaltyAbility(effect, 1);
        effect = new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.UntilYourNextTurn, StaticFilters.FILTER_PERMANENT_CREATURES);
        effect.setText("and gain lifelink");
        loyaltyAbility.addEffect(effect);
        this.addAbility(loyaltyAbility);

        // -2: Create a 2/2 black Vampire creature token with flying.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new SorinSolemnVisitorVampireToken()), -2));

        // -6: You get an emblem with "At the beginning of each opponent's upkeep, that player sacrifices a creature."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new SorinSolemnVisitorEmblem()), -6));

    }

    private SorinSolemnVisitor(final SorinSolemnVisitor card) {
        super(card);
    }

    @Override
    public SorinSolemnVisitor copy() {
        return new SorinSolemnVisitor(this);
    }
}