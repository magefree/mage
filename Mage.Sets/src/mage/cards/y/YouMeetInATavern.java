package mage.cards.y;

import mage.abilities.Mode;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YouMeetInATavern extends CardImpl {

    public YouMeetInATavern(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{G}");

        // Choose one —
        // • Form a Party — Look at the top five cards of your library. You may reveal any number of creature cards from among them and put them into your hand. Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                5, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_CREATURES, PutCards.HAND, PutCards.BOTTOM_RANDOM));
        this.getSpellAbility().withFirstModeFlavorWord("Form a Party");

        // • Start a Brawl — Creatures you control get +2/+2 until end of turn.
        this.getSpellAbility().addMode(new Mode(new BoostControlledEffect(
                2, 2, Duration.EndOfTurn
        )).withFlavorWord("Start a Brawl"));
    }

    private YouMeetInATavern(final YouMeetInATavern card) {
        super(card);
    }

    @Override
    public YouMeetInATavern copy() {
        return new YouMeetInATavern(this);
    }
}
