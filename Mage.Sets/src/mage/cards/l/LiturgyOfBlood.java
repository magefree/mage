
package mage.cards.l;

import java.util.UUID;
import mage.Mana;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class LiturgyOfBlood extends CardImpl {

    public LiturgyOfBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");


        // Destroy target creature. Add {B}{B}{B}.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BasicManaEffect(Mana.BlackMana(3)));
    }

    private LiturgyOfBlood(final LiturgyOfBlood card) {
        super(card);
    }

    @Override
    public LiturgyOfBlood copy() {
        return new LiturgyOfBlood(this);
    }
}
