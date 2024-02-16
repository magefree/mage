package mage.cards.k;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class KodamasMight extends CardImpl {

    public KodamasMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");
        this.subtype.add(SubType.ARCANE);


        // Target creature gets +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2,2, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("gets +2/+2"));
        // Splice onto Arcane {G}
        this.addAbility(new SpliceAbility(SpliceAbility.ARCANE, "{G}"));
    }

    private KodamasMight(final KodamasMight card) {
        super(card);
    }

    @Override
    public KodamasMight copy() {
        return new KodamasMight(this);
    }
}
