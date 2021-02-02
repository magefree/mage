
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventCombatDamageToSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class SeraphOfTheSword extends CardImpl {

    public SeraphOfTheSword(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Prevent all combat damage that would be dealt to Seraph of the Sword.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventCombatDamageToSourceEffect(Duration.WhileOnBattlefield)));
    }

    private SeraphOfTheSword(final SeraphOfTheSword card) {
        super(card);
    }

    @Override
    public SeraphOfTheSword copy() {
        return new SeraphOfTheSword(this);
    }
}
