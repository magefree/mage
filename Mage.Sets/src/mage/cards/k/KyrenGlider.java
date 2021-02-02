
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author BursegSardaukar
 */
public final class KyrenGlider extends CardImpl {

    public KyrenGlider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Kyren Glider can't block.
        Ability ability = new CantBlockAbility();
        this.addAbility(ability);
    }

    private KyrenGlider(final KyrenGlider card) {
        super(card);
    }

    @Override
    public KyrenGlider copy() {
        return new KyrenGlider(this);
    }
}