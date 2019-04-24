
package mage.cards.c;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author cbt33, jonubuu (Withered Wretch)
 */

public final class CoffinPurge extends CardImpl {

    public CoffinPurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");


        // Exile target card from a graveyard.
        
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard());
        // Flashback {B}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{B}"), TimingRule.INSTANT));
    }

    public CoffinPurge(final CoffinPurge card) {
        super(card);
    }

    @Override
    public CoffinPurge copy() {
        return new CoffinPurge(this);
    }
}
