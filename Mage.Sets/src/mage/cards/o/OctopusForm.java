package mage.cards.o;

import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OctopusForm extends CardImpl {

    public OctopusForm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        this.subtype.add(SubType.LESSON);

        // Target creature you control gets +1/+1 and gains hexproof until end of turn. Untap it.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                1, 1, Duration.EndOfTurn
        ).setText("target creature you control gets +1/+1"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains hexproof until end of turn"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new UntapTargetEffect("Untap it"));
    }

    private OctopusForm(final OctopusForm card) {
        super(card);
    }

    @Override
    public OctopusForm copy() {
        return new OctopusForm(this);
    }
}
