package mage.cards.i;

import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class IntoTheFray extends CardImpl {

    public IntoTheFray(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");
        this.subtype.add(SubType.ARCANE);

        // Target creature attacks this turn if able.
        this.getSpellAbility().addEffect(new AttacksIfAbleTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("attacks this turn if able"));

        // Splice onto Arcane {R}
        this.addAbility(new SpliceAbility(SpliceAbility.ARCANE, "{R}"));
    }

    private IntoTheFray(final IntoTheFray card) {
        super(card);
    }

    @Override
    public IntoTheFray copy() {
        return new IntoTheFray(this);
    }
}
