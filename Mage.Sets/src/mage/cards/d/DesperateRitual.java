
package mage.cards.d;

import java.util.UUID;
import mage.Mana;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class DesperateRitual extends CardImpl {

    public DesperateRitual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");
        this.subtype.add(SubType.ARCANE);

        // Add {R}{R}{R}.
        this.getSpellAbility().addEffect(new BasicManaEffect(Mana.RedMana(3)));
        // Splice onto Arcane {1}{R}
        this.addAbility(new SpliceAbility(SpliceAbility.ARCANE, "{1}{R}"));
    }

    private DesperateRitual(final DesperateRitual card) {
        super(card);
    }

    @Override
    public DesperateRitual copy() {
        return new DesperateRitual(this);
    }
}
