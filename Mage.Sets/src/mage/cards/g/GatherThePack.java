package mage.cards.g;

import java.util.UUID;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.effects.common.RevealLibraryPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author awjackson
 */
public final class GatherThePack extends CardImpl {

    private static final String rule = "Reveal the top five cards of your library. " +
            "You may put a creature card from among them into your hand. Put the rest into your graveyard.<br>" +
            AbilityWord.SPELL_MASTERY.formatWord() + "If " + SpellMasteryCondition.instance.toString() +
            ", put up to two creature cards from among the revealed cards into your hand instead of one";

    public GatherThePack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Reveal the top five cards of your library. You may put a creature card from among them into your hand. Put the rest into your graveyard.
        // <i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, put up to two creature cards from among the revealed cards into your hand instead of one.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new RevealLibraryPickControllerEffect(5, 2, StaticFilters.FILTER_CARD_CREATURE_A, PutCards.HAND, PutCards.GRAVEYARD),
                new RevealLibraryPickControllerEffect(5, 1, StaticFilters.FILTER_CARD_CREATURE_A, PutCards.HAND, PutCards.GRAVEYARD),
                SpellMasteryCondition.instance, rule
        ));
    }

    private GatherThePack(final GatherThePack card) {
        super(card);
    }

    @Override
    public GatherThePack copy() {
        return new GatherThePack(this);
    }
}
