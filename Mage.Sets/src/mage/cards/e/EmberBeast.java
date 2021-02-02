
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CantAttackAloneAbility;
import mage.abilities.keyword.CantBlockAloneAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class EmberBeast extends CardImpl {

    public EmberBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Ember Beast can't attack or block alone.
        this.addAbility(new CantAttackAloneAbility());
        this.addAbility(CantBlockAloneAbility.getInstance());
    }

    private EmberBeast(final EmberBeast card) {
        super(card);
    }

    @Override
    public EmberBeast copy() {
        return new EmberBeast(this);
    }
}
