
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author North
 */
public final class PristineTalisman extends CardImpl {

    public PristineTalisman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        ColorlessManaAbility ability = new ColorlessManaAbility();
        ability.addEffect(new GainLifeEffect(1));
        this.addAbility(ability);
    }

    private PristineTalisman(final PristineTalisman card) {
        super(card);
    }

    @Override
    public PristineTalisman copy() {
        return new PristineTalisman(this);
    }
}
