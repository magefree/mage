package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class SlayersStronghold extends CardImpl {

    public SlayersStronghold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {R}{W}, {tap}: Target creature gets +2/+0 and gains vigilance and haste until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(
                2, 0, Duration.EndOfTurn
        ).setText("target creature gets +2/+0"), new ManaCostsImpl<>("{R}{W}"));
        ability.addEffect(new GainAbilityTargetEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains vigilance"));
        ability.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("and haste until end of turn"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SlayersStronghold(final SlayersStronghold card) {
        super(card);
    }

    @Override
    public SlayersStronghold copy() {
        return new SlayersStronghold(this);
    }
}
