package mage.cards.s;

import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.RevealLibraryPickControllerEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class SeeTheUnwritten extends CardImpl {

    private static final String rule = "Reveal the top eight cards of your library. " +
            "You may put a creature card from among them onto the battlefield. Put the rest into your graveyard.<br>" +
            AbilityWord.FEROCIOUS.formatWord() + "If " + FerociousCondition.instance.toString() +
            ", you may put two creature cards onto the battlefield instead of one";

    public SeeTheUnwritten(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");

        // Reveal the top eight cards of your library. You may put a creature card from among them onto the battlefield. Put the rest into your graveyard.
        // <i>Ferocious</i> &mdash; If you control a creature with power 4 or greater, you may put two creature cards onto the battlefield instead of one.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new RevealLibraryPickControllerEffect(8, 2, StaticFilters.FILTER_CARD_CREATURE_A, PutCards.BATTLEFIELD, PutCards.GRAVEYARD),
                new RevealLibraryPickControllerEffect(8, 1, StaticFilters.FILTER_CARD_CREATURE_A, PutCards.BATTLEFIELD, PutCards.GRAVEYARD),
                FerociousCondition.instance, rule
        ));
        this.getSpellAbility().addHint(FerociousHint.instance);
    }

    private SeeTheUnwritten(final SeeTheUnwritten card) {
        super(card);
    }

    @Override
    public SeeTheUnwritten copy() {
        return new SeeTheUnwritten(this);
    }
}
