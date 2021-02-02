
package mage.cards.l;

import java.util.UUID;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;

/**
 * @author LevelX2
 */
public final class LibraryOfAlexandria extends CardImpl {

    public LibraryOfAlexandria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {tap}: Draw a card. Activate this ability only if you have exactly seven cards in hand.
        this.addAbility(new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(1),
                new TapSourceCost(),
                new CardsInHandCondition(ComparisonType.EQUAL_TO, 7),
                ""));
    }

    private LibraryOfAlexandria(final LibraryOfAlexandria card) {
        super(card);
    }

    @Override
    public LibraryOfAlexandria copy() {
        return new LibraryOfAlexandria(this);
    }
}
