
package mage.cards.r;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class RootOut extends CardImpl {

    public RootOut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");

        // Destroy target artifact or enchantment.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        // Investigate.
        Effect effect = new InvestigateEffect();
        effect.setText("<br>Investigate. <i>(Create a colorless Clue artifact token with \"{2}, Sacrifice this artifact: Draw a card.\")</i>");
        this.getSpellAbility().addEffect(effect);
    }

    private RootOut(final RootOut card) {
        super(card);
    }

    @Override
    public RootOut copy() {
        return new RootOut(this);
    }
}
