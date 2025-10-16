package mage.cards.w;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.effects.common.replacement.GraveyardFromAnywhereExileReplacementEffect;
import mage.abilities.effects.common.ruleModifying.PlayFromGraveyardControllerEffect;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class WalkInClosetForgottenCellar extends RoomCard {

    public WalkInClosetForgottenCellar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}", "{3}{G}{G}", SpellAbilityType.SPLIT);
        this.subtype.add(SubType.ROOM);

        // Walk-In Closet: You may play lands from your graveyard.
        SimpleStaticAbility left = new SimpleStaticAbility(PlayFromGraveyardControllerEffect.playLands());

        // Forgotten Cellar: When you unlock this door, you may cast spells from your graveyard this turn, and if a card would be put into your graveyard from anywhere this turn, exile it instead.
        UnlockThisDoorTriggeredAbility right = new UnlockThisDoorTriggeredAbility(
                new PlayFromGraveyardControllerEffect(StaticFilters.FILTER_CARD_NON_LAND, Duration.EndOfTurn)
                        .setText("you may cast spells from your graveyard this turn"), false, false
        );
        right.addEffect(new GraveyardFromAnywhereExileReplacementEffect(Duration.EndOfTurn).concatBy(", and")
                .setText("if a card would be put into your graveyard from anywhere this turn, exile it instead")
        );

        this.addRoomAbilities(left, right);
    }

    private WalkInClosetForgottenCellar(final WalkInClosetForgottenCellar card) {
        super(card);
    }

    @Override
    public WalkInClosetForgottenCellar copy() {
        return new WalkInClosetForgottenCellar(this);
    }
}
