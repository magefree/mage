
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.EscalateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class BorrowedMalevolence extends CardImpl {

    private static final FilterCreaturePermanent filterCreaturePlus = new FilterCreaturePermanent("creature to get +1/+1");
    private static final FilterCreaturePermanent filterCreatureMinus = new FilterCreaturePermanent("creature to get -1/-1");

    public BorrowedMalevolence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        // Escalate {2}
        this.addAbility(new EscalateAbility(new GenericManaCost(2)));

        // Choose one or both &mdash
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // Target creature gets +1/+1 until end of turn.
        Effect effect = new BoostTargetEffect(1, 1, Duration.EndOfTurn);
        effect.setText("Target creature gets +1/+1 until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filterCreaturePlus));

        // Target creature gets -1/-1 until end of turn.
        Mode mode = new Mode();
        effect = new BoostTargetEffect(-1, -1, Duration.EndOfTurn);
        effect.setText("Target creature gets -1/-1 until end of turn");
        mode.getEffects().add(effect);
        mode.getTargets().add(new TargetCreaturePermanent(filterCreatureMinus));
        this.getSpellAbility().addMode(mode);
    }

    public BorrowedMalevolence(final BorrowedMalevolence card) {
        super(card);
    }

    @Override
    public BorrowedMalevolence copy() {
        return new BorrowedMalevolence(this);
    }
}
