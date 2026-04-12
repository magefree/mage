package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.GrandeurAbility;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;

/**
 *
 * @author muz
 */
public final class PageLooseLeaf extends CardImpl {

    public PageLooseLeaf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // Grandeur -- Discard another card named Page, Loose Leaf:
        // Reveal cards from the top of your library until you reveal an instant or sorcery card.
        // Put that card into your hand and the rest on the bottom of your library in a random order.
        Ability ability = new GrandeurAbility(
            new RevealCardsFromLibraryUntilEffect(
                StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY,
                PutCards.HAND,
                PutCards.BOTTOM_RANDOM
            ),
            "Page, Loose Leaf");
        this.addAbility(ability);
    }

    private PageLooseLeaf(final PageLooseLeaf card) {
        super(card);
    }

    @Override
    public PageLooseLeaf copy() {
        return new PageLooseLeaf(this);
    }
}
