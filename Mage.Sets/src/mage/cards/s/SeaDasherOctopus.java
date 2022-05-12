package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.MutateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeaDasherOctopus extends CardImpl {

    public SeaDasherOctopus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.subtype.add(SubType.OCTOPUS);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Mutate {1}{U}
        this.addAbility(new MutateAbility(this, "{1}{U}"));

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Whenever this creature deals combat damage to a player, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false
        ).setTriggerPhrase("Whenever this creature deals combat damage to a player, "));
    }

    private SeaDasherOctopus(final SeaDasherOctopus card) {
        super(card);
    }

    @Override
    public SeaDasherOctopus copy() {
        return new SeaDasherOctopus(this);
    }
}
