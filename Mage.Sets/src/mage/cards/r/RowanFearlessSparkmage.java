package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RowanFearlessSparkmage extends CardImpl {

    public RowanFearlessSparkmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROWAN);
        this.setStartingLoyalty(5);

        // +1: Up to one target creature gets +3/+0 and gains first strike until end of turn.
        Ability ability = new LoyaltyAbility(new BoostTargetEffect(
                3, 0, Duration.EndOfTurn
        ).setText("up to one target creature gets +3/+0"), 1);
        ability.addEffect(new GainAbilityTargetEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains first strike until end of turn"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // −2: Rowan, Fearless Sparkmage deals 1 damage to each of up to two target creatures. Those creatures can't block this turn.
        ability = new LoyaltyAbility(new DamageTargetEffect(1)
                .setText("{this} deals 1 damage to each of up to two target creatures"), -2);
        ability.addEffect(new CantBlockTargetEffect(Duration.EndOfTurn)
                .setText("those creatures can't block this turn"));
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(ability);

        // −9: Gain control of all creatures until end of turn. Untap them. They gain haste until end of turn.
        ability = new LoyaltyAbility(new GainControlAllEffect(Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES), -9);
        ability.addEffect(new UntapAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES).setText("untap them"));
        ability.addEffect(new GainAbilityAllEffect(
                HasteAbility.getInstance(),
                Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES,
                "they gain haste until end of turn"
        ));
        this.addAbility(ability);
    }

    private RowanFearlessSparkmage(final RowanFearlessSparkmage card) {
        super(card);
    }

    @Override
    public RowanFearlessSparkmage copy() {
        return new RowanFearlessSparkmage(this);
    }
}
