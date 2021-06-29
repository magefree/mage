package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShortcutSeeker extends CardImpl {

    public ShortcutSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Whenever Shortcut Seeker deals combat damage to a player, venture into the dungeon.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new VentureIntoTheDungeonEffect(), false
        ));
    }

    private ShortcutSeeker(final ShortcutSeeker card) {
        super(card);
    }

    @Override
    public ShortcutSeeker copy() {
        return new ShortcutSeeker(this);
    }
}
