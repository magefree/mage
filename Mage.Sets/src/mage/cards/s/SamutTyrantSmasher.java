package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public final class SamutTyrantSmasher extends CardImpl {

    public SamutTyrantSmasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R/G}{R/G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SAMUT);
        this.setStartingLoyalty(5);

        // Creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES
        )));

        // -1: Target creature gets +2/+1 and gains haste until end of turn. Scry 1.
        Ability ability = new LoyaltyAbility(new BoostTargetEffect(
                2, 1, Duration.EndOfTurn
        ).setText("target creature gets +2/+1"), -1);
        ability.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains haste until end of turn"));
        ability.addEffect(new ScryEffect(1, false));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SamutTyrantSmasher(final SamutTyrantSmasher card) {
        super(card);
    }

    @Override
    public SamutTyrantSmasher copy() {
        return new SamutTyrantSmasher(this);
    }
}
