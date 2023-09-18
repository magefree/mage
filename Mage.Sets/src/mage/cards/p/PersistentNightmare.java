package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.SkulkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PersistentNightmare extends CardImpl {

    public PersistentNightmare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.color.setBlue(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Skulk
        this.addAbility(new SkulkAbility());

        // When Persistent Nightmare deals combat damage to a player, return it to its owner's hand.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new ReturnToHandSourceEffect(), false
        ).setTriggerPhrase("When {this} deals combat damage to a player, "));
    }

    private PersistentNightmare(final PersistentNightmare card) {
        super(card);
    }

    @Override
    public PersistentNightmare copy() {
        return new PersistentNightmare(this);
    }
}
