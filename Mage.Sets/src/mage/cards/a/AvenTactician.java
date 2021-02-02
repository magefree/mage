
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class AvenTactician extends CardImpl {

    public AvenTactician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
            
        // When Aven Tactician enters the battlefield, bolster 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BolsterEffect(1)));
    }

    private AvenTactician(final AvenTactician card) {
        super(card);
    }

    @Override
    public AvenTactician copy() {
        return new AvenTactician(this);
    }
}
