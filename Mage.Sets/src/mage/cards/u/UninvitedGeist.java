
package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.SkulkAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class UninvitedGeist extends CardImpl {

    public UninvitedGeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.secondSideCardClazz = mage.cards.u.UnimpededTrespasser.class;

        // Skulk (This creature can't be blocked by creatures with greater power.)
        this.addAbility(new SkulkAbility());

        // When Uninvited Geist deals combat damage to a player, transform it.
        this.addAbility(new TransformAbility());
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new TransformSourceEffect(), false));

    }

    private UninvitedGeist(final UninvitedGeist card) {
        super(card);
    }

    @Override
    public UninvitedGeist copy() {
        return new UninvitedGeist(this);
    }
}
