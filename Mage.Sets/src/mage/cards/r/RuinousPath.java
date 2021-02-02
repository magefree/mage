
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.AwakenAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author LevelX2
 */
public final class RuinousPath extends CardImpl {

    public RuinousPath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}{B}");

        // Destroy target creature or planeswalker.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());

        // Awaken 4-{5}{B}{B}
        this.addAbility(new AwakenAbility(this, 4, "{5}{B}{B}"));
    }

    private RuinousPath(final RuinousPath card) {
        super(card);
    }

    @Override
    public RuinousPath copy() {
        return new RuinousPath(this);
    }
}
