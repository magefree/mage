package mage.cards.p;

import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PedalToTheMetal extends CardImpl {

    public PedalToTheMetal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}");

        // Target creature gets +X/+0 and gains first strike until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(GetXValue.instance, StaticValue.get(0))
                .setText("target creature gets +X/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance())
                .setText("and gains first strike until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private PedalToTheMetal(final PedalToTheMetal card) {
        super(card);
    }

    @Override
    public PedalToTheMetal copy() {
        return new PedalToTheMetal(this);
    }
}
