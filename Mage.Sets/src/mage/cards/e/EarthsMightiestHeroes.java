package mage.cards.e;

import java.util.UUID;
import mage.abilities.condition.common.TeamworkCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.RevealLibraryPickControllerEffect;
import mage.abilities.keyword.TeamworkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;

/**
 *
 * @author nandmp
 */
public final class EarthsMightiestHeroes extends CardImpl {

    public EarthsMightiestHeroes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");

        // Teamwork 5
        this.addAbility(new TeamworkAbility(5));

        // Reveal the top eight cards of your library. You may put a creature card from among them onto the battlefield. If this spell was cast using teamwork, put any number of creature cards from among them onto the battlefield instead. Put the rest into your graveyard.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new RevealLibraryPickControllerEffect(8, 8, StaticFilters.FILTER_CARD_CREATURE_A,
                        PutCards.BATTLEFIELD, PutCards.GRAVEYARD),
                new RevealLibraryPickControllerEffect(8, 1, StaticFilters.FILTER_CARD_CREATURE_A,
                        PutCards.BATTLEFIELD, PutCards.GRAVEYARD),
                TeamworkCondition.instance,
                "reveal the top eight cards of your library. You may put a creature card from among them " +
                        "onto the battlefield. If this spell was cast using teamwork, put any number of creature " +
                        "cards from among them onto the battlefield instead. Put the rest into your graveyard"
        ));
    }

    private EarthsMightiestHeroes(final EarthsMightiestHeroes card) {
        super(card);
    }

    @Override
    public EarthsMightiestHeroes copy() {
        return new EarthsMightiestHeroes(this);
    }
}
