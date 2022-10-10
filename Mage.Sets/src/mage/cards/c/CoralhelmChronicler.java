package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CoralhelmChronicler extends CardImpl {

    private static final FilterCard filter = new FilterCard("card with a kicker ability");

    static {
        filter.add(new AbilityPredicate(KickerAbility.class));
    }

    public CoralhelmChronicler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a kicked spell, draw a card, then discard a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawDiscardControllerEffect(1, 1, false),
                StaticFilters.FILTER_SPELL_KICKED_A, false
        ));

        // When Coralhelm Chronicler enters the battlefield, look at the top five cards of your library. You may reveal a card with a kicker ability from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                5, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM)));
    }

    private CoralhelmChronicler(final CoralhelmChronicler card) {
        super(card);
    }

    @Override
    public CoralhelmChronicler copy() {
        return new CoralhelmChronicler(this);
    }
}
