
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.watchers.common.CastFromHandWatcher;

/**
 *
 * @author ilcartographer
 */
public final class CoalStoker extends CardImpl {

    public CoalStoker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Coal Stoker enters the battlefield, if you cast it from your hand, add {R}{R}{R}.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new BasicManaEffect(Mana.RedMana(3)), false),
                CastFromHandSourcePermanentCondition.instance,
                "When {this} enters the battlefield, if you cast it from your hand, add {R}{R}{R}."),
                new CastFromHandWatcher());
    }

    private CoalStoker(final CoalStoker card) {
        super(card);
    }

    @Override
    public CoalStoker copy() {
        return new CoalStoker(this);
    }
}
