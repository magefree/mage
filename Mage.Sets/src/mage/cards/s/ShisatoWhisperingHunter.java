package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.SkipNextPlayerUntapStepEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author LevelX
 */
public final class ShisatoWhisperingHunter extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SNAKE);

    public ShisatoWhisperingHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, sacrifice a Snake.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeControllerEffect(filter, 1, "")));

        // Whenever Shisato, Whispering Hunter deals combat damage to a player, that player skips their next untap step.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new SkipNextPlayerUntapStepEffect("that player"), false, true
        ));
    }

    private ShisatoWhisperingHunter(final ShisatoWhisperingHunter card) {
        super(card);
    }

    @Override
    public ShisatoWhisperingHunter copy() {
        return new ShisatoWhisperingHunter(this);
    }
}
