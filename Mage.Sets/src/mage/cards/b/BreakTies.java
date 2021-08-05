package mage.cards.b;

import mage.abilities.Mode;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.ReinforceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BreakTies extends CardImpl {

    public BreakTies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Choose one —
        // • Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());

        // • Destroy target enchantment.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().addMode(mode);

        // • Exile target card from a graveyard.
        mode = new Mode(new ExileTargetEffect());
        mode.addTarget(new TargetCardInGraveyard());
        this.getSpellAbility().addMode(mode);

        // Reinforce 1—{W}
        this.addAbility(new ReinforceAbility(1, new ManaCostsImpl<>("{W}")));
    }

    private BreakTies(final BreakTies card) {
        super(card);
    }

    @Override
    public BreakTies copy() {
        return new BreakTies(this);
    }
}
