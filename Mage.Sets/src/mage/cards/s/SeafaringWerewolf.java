package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeafaringWerewolf extends CardImpl {

    public SeafaringWerewolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.color.setGreen(true);
        this.nightCard = true;

        // Seafaring Werewolf can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        // Whenever Seafaring Werewolf deals combat damage to a player, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false
        ));

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private SeafaringWerewolf(final SeafaringWerewolf card) {
        super(card);
    }

    @Override
    public SeafaringWerewolf copy() {
        return new SeafaringWerewolf(this);
    }
}
