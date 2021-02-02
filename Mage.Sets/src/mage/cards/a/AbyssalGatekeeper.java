
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author daagar
 */
public final class AbyssalGatekeeper extends CardImpl {

    public AbyssalGatekeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Abyssal Gatekeeper dies, each player sacrifices a creature.
        this.addAbility(new DiesSourceTriggeredAbility(new SacrificeAllEffect(1, new FilterControlledCreaturePermanent("creature"))));
    }

    private AbyssalGatekeeper(final AbyssalGatekeeper card) {
        super(card);
    }

    @Override
    public AbyssalGatekeeper copy() {
        return new AbyssalGatekeeper(this);
    }
}
