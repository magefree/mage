package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LightningSecuritySergeant extends CardImpl {

    public LightningSecuritySergeant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Lightning deals combat damage to a player, exile the top card of your library. You may play that card for as long as you control Lightning.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new ExileTopXMayPlayUntilEffect(1, Duration.WhileControlled)
        ));
    }

    private LightningSecuritySergeant(final LightningSecuritySergeant card) {
        super(card);
    }

    @Override
    public LightningSecuritySergeant copy() {
        return new LightningSecuritySergeant(this);
    }
}
