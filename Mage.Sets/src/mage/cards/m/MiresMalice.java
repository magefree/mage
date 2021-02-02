
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.AwakenAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class MiresMalice extends CardImpl {

    public MiresMalice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}");

        // Target opponent discards two cards.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetOpponent());
        // Awaken 3 - {5}{B}
        this.addAbility(new AwakenAbility(this, 3, "{5}{B}"));
    }

    private MiresMalice(final MiresMalice card) {
        super(card);
    }

    @Override
    public MiresMalice copy() {
        return new MiresMalice(this);
    }
}
