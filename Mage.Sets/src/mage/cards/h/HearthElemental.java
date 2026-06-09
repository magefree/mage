package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AdventurePredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class HearthElemental extends AdventureCard {

    private static final FilterCard filter = new FilterCard("cards in your graveyard that are instant cards, sorcery cards, and/or have an Adventure");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate(),
                AdventurePredicate.instance
        ));
    }

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(filter);
    private static final Hint hint = new ValueHint("Number of Instant, Sorcery and/or Adventures in your graveyard", xValue);

    public HearthElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELEMENTAL}, "{5}{R}",
                "Stoke Genius",
                new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Hearth Elemental
        this.getLeftHalfCard().setPT(4, 5);

        // This spell costs X less to cast, where X is the number of cards in your graveyard that are instant cards, sorcery cards, and/or have an Adventure.
        Ability ability = new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue)
                .setText("This spell costs {X} less to cast, where X is the number of cards in your graveyard " +
                        "that are instant cards, sorcery cards, and/or have an Adventure")
        ).addHint(hint);
        this.getLeftHalfCard().addAbility(ability);

        // Stoke Genius
        // Discard your hand, then draw two cards.
        this.getRightHalfCard().getSpellAbility().addEffect(new DiscardHandControllerEffect());
        this.getRightHalfCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2)
                .concatBy(", then"));

        finalizeCard();
    }

    private HearthElemental(final HearthElemental card) {
        super(card);
    }

    @Override
    public HearthElemental copy() {
        return new HearthElemental(this);
    }
}
