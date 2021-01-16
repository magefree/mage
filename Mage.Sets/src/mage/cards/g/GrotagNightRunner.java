package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrotagNightRunner extends CardImpl {

    public GrotagNightRunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Grotag Night-Runner deals combat damage to a player, exile the top card of your library. You may play that card this turn.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ExileTopXMayPlayUntilEndOfTurnEffect(1), false));
    }

    private GrotagNightRunner(final GrotagNightRunner card) {
        super(card);
    }

    @Override
    public GrotagNightRunner copy() {
        return new GrotagNightRunner(this);
    }
}
