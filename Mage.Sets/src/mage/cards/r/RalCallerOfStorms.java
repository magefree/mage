package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author TheElk801
 */
public final class RalCallerOfStorms extends CardImpl {

    public static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature your opponents control");

    public RalCallerOfStorms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RAL);
        this.setStartingLoyalty(4);

        // +1: Draw a card.
        this.addAbility(new LoyaltyAbility(
                new DrawCardSourceControllerEffect(1), 1
        ));

        // -2: Ral, Caller of Storms deals 3 damage divided as you choose among one, two, or three targets.
        Ability ability = new LoyaltyAbility(new DamageMultiEffect(3), -2);
        ability.addTarget(new TargetAnyTargetAmount(3));
        this.addAbility(ability);

        // -7: Draw seven cards. Ral, Caller of Storms deals 7 damage to each creature your opponents control.
        ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(7), -7);
        ability.addEffect(new DamageAllEffect(7, filter));
        this.addAbility(ability);
    }

    private RalCallerOfStorms(final RalCallerOfStorms card) {
        super(card);
    }

    @Override
    public RalCallerOfStorms copy() {
        return new RalCallerOfStorms(this);
    }
}
