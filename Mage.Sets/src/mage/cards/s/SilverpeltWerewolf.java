package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class SilverpeltWerewolf extends CardImpl {

    public SilverpeltWerewolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, null);
        this.subtype.add(SubType.WEREWOLF);
        this.color.setGreen(true);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        this.nightCard = true;

        // Whenever Silverpelt Werewolf deals combat damage to a player, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1), false));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Silverpelt Werewolf.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private SilverpeltWerewolf(final SilverpeltWerewolf card) {
        super(card);
    }

    @Override
    public SilverpeltWerewolf copy() {
        return new SilverpeltWerewolf(this);
    }
}
