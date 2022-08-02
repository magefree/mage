package mage.cards.p;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.SweepNumber;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.SweepEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class PlowThroughReito extends CardImpl {

    public PlowThroughReito(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");
        this.subtype.add(SubType.ARCANE);

        // Sweep - Return any number of Plains you control to their owner's hand. Target creature gets +1/+1 until end of turn for each Plains returned this way.
        this.getSpellAbility().addEffect(new SweepEffect(SubType.PLAINS));
        this.getSpellAbility().addEffect(new BoostTargetEffect(SweepNumber.PLAINS, SweepNumber.PLAINS, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private PlowThroughReito(final PlowThroughReito card) {
        super(card);
    }

    @Override
    public PlowThroughReito copy() {
        return new PlowThroughReito(this);
    }
}
