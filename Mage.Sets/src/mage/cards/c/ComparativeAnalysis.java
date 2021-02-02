
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.keyword.SurgeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class ComparativeAnalysis extends CardImpl {

    public ComparativeAnalysis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");
        
        // Target player draws two cards.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetPlayer());
        
        // Surge {2}{U} <You may cast this spell for its surge cost if you or a teammate has cast another spell this turn.)</i>
        addAbility(new SurgeAbility(this, "{2}{U}"));
    }

    private ComparativeAnalysis(final ComparativeAnalysis card) {
        super(card);
    }

    @Override
    public ComparativeAnalysis copy() {
        return new ComparativeAnalysis(this);
    }
}
