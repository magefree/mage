
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.ExploreSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class JadelightRanger extends CardImpl {

    public JadelightRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Jadelight Ranger enters the battlefield, it explores, then it explores again.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExploreSourceEffect(false));
        ability.addEffect(new ExploreSourceEffect(true, (byte) 1));
        this.addAbility(ability);
    }

    public JadelightRanger(final JadelightRanger card) {
        super(card);
    }

    @Override
    public JadelightRanger copy() {
        return new JadelightRanger(this);
    }
}
