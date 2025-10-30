package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class FuneralRoomAwakeningHall extends RoomCard {

    public FuneralRoomAwakeningHall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}", "{6}{B}{B}", SpellAbilityType.SPLIT);
        this.subtype.add(SubType.ROOM);

        // Funeral Room: Whenever a creature you control dies, each opponent loses 1 life and you gain 1 life.
        Ability left = new DiesCreatureTriggeredAbility(
                new LoseLifeOpponentsEffect(1), false,
                StaticFilters.FILTER_CONTROLLED_A_CREATURE
        );
        left.addEffect(new GainLifeEffect(1).concatBy("and"));

        // Awakening Hall: When you unlock this door, return all creature cards from your graveyard to the battlefield.
        Ability right = new UnlockThisDoorTriggeredAbility(
                new ReturnFromYourGraveyardToBattlefieldAllEffect(StaticFilters.FILTER_CARD_CREATURES), false, false
        );

        this.addRoomAbilities(left, right);
    }

    private FuneralRoomAwakeningHall(final FuneralRoomAwakeningHall card) {
        super(card);
    }

    @Override
    public FuneralRoomAwakeningHall copy() {
        return new FuneralRoomAwakeningHall(this);
    }
}
