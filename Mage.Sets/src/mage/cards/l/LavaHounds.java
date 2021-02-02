
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class LavaHounds extends CardImpl {

    public LavaHounds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // When Lava Hounds enters the battlefield, it deals 4 damage to you.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DamageControllerEffect(4, "it")));
    }

    private LavaHounds(final LavaHounds card) {
        super(card);
    }

    @Override
    public LavaHounds copy() {
        return new LavaHounds(this);
    }
}
