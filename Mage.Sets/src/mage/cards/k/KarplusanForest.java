
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.mana.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class KarplusanForest extends CardImpl {

    public KarplusanForest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        
        this.addAbility(new ColorlessManaAbility());

        Ability redManaAbility = new RedManaAbility();
        redManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(redManaAbility);
        Ability greenManaAbility = new GreenManaAbility();
        greenManaAbility.addEffect(new DamageControllerEffect(1));
        this.addAbility(greenManaAbility);
    }

    private KarplusanForest(final KarplusanForest card) {
        super(card);
    }

    @Override
    public KarplusanForest copy() {
        return new KarplusanForest(this);
    }
}
