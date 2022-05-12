package mage.cards.v;

import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoidRend extends CardImpl {

    public VoidRend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{U}{B}");

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility().setRuleAtTheTop(true));

        // Destroy target nonland permanent.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private VoidRend(final VoidRend card) {
        super(card);
    }

    @Override
    public VoidRend copy() {
        return new VoidRend(this);
    }
}
