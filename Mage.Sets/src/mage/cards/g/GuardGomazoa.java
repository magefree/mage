
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventCombatDamageToSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author jeffwadsworth
 */
public final class GuardGomazoa extends CardImpl {

    public GuardGomazoa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.JELLYFISH);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(FlyingAbility.getInstance());

        // Prevent all combat damage that would be dealt to Guard Gomazoa.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventCombatDamageToSourceEffect(Duration.WhileOnBattlefield)));
    }

    private GuardGomazoa(final GuardGomazoa card) {
        super(card);
    }

    @Override
    public GuardGomazoa copy() {
        return new GuardGomazoa(this);
    }
}
