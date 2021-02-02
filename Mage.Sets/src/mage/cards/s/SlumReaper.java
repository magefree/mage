
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SlumReaper extends CardImpl {

    public SlumReaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Slum Reaper enters the battlefield, each player sacrifices a creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeAllEffect(1, new FilterControlledCreaturePermanent("creature"))));
    }

    private SlumReaper(final SlumReaper card) {
        super(card);
    }

    @Override
    public SlumReaper copy() {
        return new SlumReaper(this);
    }
}