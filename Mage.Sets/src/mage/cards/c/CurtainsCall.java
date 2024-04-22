
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.UndauntedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class CurtainsCall extends CardImpl {

    public CurtainsCall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{B}");

        // Undaunted
        this.addAbility(new UndauntedAbility());
        // Destroy two target creatures.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(2));
    }

    private CurtainsCall(final CurtainsCall card) {
        super(card);
    }

    @Override
    public CurtainsCall copy() {
        return new CurtainsCall(this);
    }
}
