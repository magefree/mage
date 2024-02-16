package mage.cards.g;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class GlacialRay extends CardImpl {

    public GlacialRay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");
        this.subtype.add(SubType.ARCANE);


        // Glacial Ray deals 2 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTarget().withChooseHint("2 damage"));
        // Splice onto Arcane {1}{R}
        this.addAbility(new SpliceAbility(SpliceAbility.ARCANE, "{1}{R}"));
    }

    private GlacialRay(final GlacialRay card) {
        super(card);
    }

    @Override
    public GlacialRay copy() {
        return new GlacialRay(this);
    }
}
