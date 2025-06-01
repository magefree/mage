package mage.cards.d;

import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterArtifactPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;
import mage.target.targetadjustment.XManaValueTargetAdjuster;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class Detonate extends CardImpl {

    public Detonate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // Destroy target artifact with converted mana cost X. It can't be regenerated. Detonate deals X damage to that artifact's controller.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetPermanent(new FilterArtifactPermanent("artifact with mana value X")));
        this.getSpellAbility().addEffect(new DamageTargetControllerEffect(GetXValue.instance, "artifact"));
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().setTargetAdjuster(new XManaValueTargetAdjuster());
    }

    private Detonate(final Detonate card) {
        super(card);
    }

    @Override
    public Detonate copy() {
        return new Detonate(this);
    }
}
