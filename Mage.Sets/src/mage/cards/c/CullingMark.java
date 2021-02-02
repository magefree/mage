
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.combat.BlocksIfAbleTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class CullingMark extends CardImpl {

    public CullingMark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");


        // Target creature blocks this turn if able.
        this.getSpellAbility().addEffect(new BlocksIfAbleTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private CullingMark(final CullingMark card) {
        super(card);
    }

    @Override
    public CullingMark copy() {
        return new CullingMark(this);
    }
}
