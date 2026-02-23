package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.command.emblems.ArlinnEmbracedByTheMoonEmblem;
import mage.game.permanent.token.WolfToken;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ArlinnKord extends TransformingDoubleFacedCard {

    public ArlinnKord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.ARLINN}, "{2}{R}{G}",
                "Arlinn, Embraced by the Moon",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.ARLINN}, "RG");
        this.getLeftHalfCard().setStartingLoyalty(3);

        // +1: Until end of turn, up to one target creature gets +2/+2 and gains vigilance and haste.
        Ability ability = new LoyaltyAbility(new BoostTargetEffect(
                2, 2, Duration.EndOfTurn
        ).setText("until end of turn, up to one target creature gets +2/+2"), 1);
        ability.addEffect(new GainAbilityTargetEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains vigilance"));
        ability.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("and haste"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.getLeftHalfCard().addAbility(ability);

        // 0: Create a 2/2 green Wolf creature token. Transform Arlinn Kord.
        ability = new LoyaltyAbility(new CreateTokenEffect(new WolfToken()), 0);
        ability.addEffect(new TransformSourceEffect());
        this.getLeftHalfCard().addAbility(ability);

        // Arlinn, Embraced by the Moon
        // +1: Creatures you control get +1/+1 and gain trample until end of turn.
        ability = new LoyaltyAbility(new BoostControlledEffect(
                1, 1, Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("Creatures you control get +1/+1"), 1);
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and gain trample until end of turn"));
        this.getRightHalfCard().addAbility(ability);

        // -1: Arlinn, Embraced by the Moon deals 3 damage to any target. Transform Arlinn, Embraced by the Moon.
        ability = new LoyaltyAbility(new DamageTargetEffect(3), -1);
        ability.addTarget(new TargetAnyTarget());
        ability.addEffect(new TransformSourceEffect());
        this.getRightHalfCard().addAbility(ability);

        // -6: You get an emblem with "Creatures you control have haste and '{T}: This creature deals damage equal to its power to any target.'"
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new GetEmblemEffect(new ArlinnEmbracedByTheMoonEmblem()), -6));

    }

    private ArlinnKord(final ArlinnKord card) {
        super(card);
    }

    @Override
    public ArlinnKord copy() {
        return new ArlinnKord(this);
    }
}
