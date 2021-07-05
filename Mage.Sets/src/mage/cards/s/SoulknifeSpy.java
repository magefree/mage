package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class SoulknifeSpy extends CardImpl {

    public SoulknifeSpy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Soulknife Spy deals combat damage to a player, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1), false));
    }

    private SoulknifeSpy(final SoulknifeSpy card) {
        super(card);
    }

    @Override
    public SoulknifeSpy copy() {
        return new SoulknifeSpy(this);
    }
}
