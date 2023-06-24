package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.continuous.GainProtectionFromColorTargetEffect;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BlessedBreath extends CardImpl {

    public BlessedBreath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");
        this.subtype.add(SubType.ARCANE);


        // Target creature you control gains protection from the color of your choice until end of turn.
        this.getSpellAbility().addEffect(new GainProtectionFromColorTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent().withChooseHint("gains protection"));

        // Splice onto Arcane {W}
        this.addAbility(new SpliceAbility(SpliceAbility.ARCANE, "{W}"));
    }

    private BlessedBreath(final BlessedBreath card) {
        super(card);
    }

    @Override
    public BlessedBreath copy() {
        return new BlessedBreath(this);
    }
}
