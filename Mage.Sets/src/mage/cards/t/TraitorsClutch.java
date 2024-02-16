package mage.cards.t;

import mage.ObjectColor;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TraitorsClutch extends CardImpl {

    public TraitorsClutch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // Target creature gets +1/+0, becomes black, and gains shadow until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                1, 0, Duration.EndOfTurn
        ).setText("target creature gets +1/+0"));
        this.getSpellAbility().addEffect(new BecomesColorTargetEffect(
                ObjectColor.BLACK, Duration.EndOfTurn
        ).setText(", becomes black"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                ShadowAbility.getInstance(), Duration.EndOfTurn
        ).setText(", and gains shadow until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Flashback {1}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{1}{B}")));
    }

    private TraitorsClutch(final TraitorsClutch card) {
        super(card);
    }

    @Override
    public TraitorsClutch copy() {
        return new TraitorsClutch(this);
    }
}
