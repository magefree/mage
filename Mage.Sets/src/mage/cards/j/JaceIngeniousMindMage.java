
package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class JaceIngeniousMindMage extends CardImpl {

    public JaceIngeniousMindMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.JACE);

        this.setStartingLoyalty(5);

        // +1: Draw a card.
        this.addAbility(new LoyaltyAbility(new DrawCardSourceControllerEffect(1), 1));

        // +1: Untap all creatures you control.
        this.addAbility(new LoyaltyAbility(new UntapAllControllerEffect(StaticFilters.FILTER_PERMANENT_CREATURES), 1));

        // -9: Gain control of up to three target creatures.
        Ability ability = new LoyaltyAbility(new GainControlTargetEffect(Duration.Custom), -9);
        ability.addTarget(new TargetCreaturePermanent(0, 3, StaticFilters.FILTER_PERMANENT_CREATURES, false));
        this.addAbility(ability);
    }

    private JaceIngeniousMindMage(final JaceIngeniousMindMage card) {
        super(card);
    }

    @Override
    public JaceIngeniousMindMage copy() {
        return new JaceIngeniousMindMage(this);
    }
}
