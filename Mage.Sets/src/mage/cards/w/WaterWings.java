package mage.cards.w;

import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class WaterWings extends CardImpl {

    public WaterWings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Until end of turn, target creature you control has base power and toughness 4/4 and gains flying and hexproof.
        this.getSpellAbility().addEffect(
                new SetBasePowerToughnessTargetEffect(4, 4, Duration.EndOfTurn)
                        .setText("until end of turn, target creature you control has base power and toughness 4/4")
        );
        this.getSpellAbility().addEffect(
                new GainAbilityTargetEffect(FlyingAbility.getInstance())
                        .setText("and gains flying")
        );
        this.getSpellAbility().addEffect(
                new GainAbilityTargetEffect(HexproofAbility.getInstance())
                        .setText("and hexproof")
        );
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private WaterWings(final WaterWings card) {
        super(card);
    }

    @Override
    public WaterWings copy() {
        return new WaterWings(this);
    }
}
