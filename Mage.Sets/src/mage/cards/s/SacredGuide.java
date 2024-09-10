
package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class SacredGuide extends CardImpl {

    private static final FilterCard filterCard = new FilterCard("white card");

    static {
        filterCard.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public SacredGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{W}, Sacrifice Sacred Guide: Reveal cards from the top of your library until you reveal a white card. Put that card into your hand and exile all other cards revealed this way.
        Ability ability = new SimpleActivatedAbility(
                new RevealCardsFromLibraryUntilEffect(filterCard, PutCards.HAND, PutCards.EXILED), new ManaCostsImpl<>("{1}{W}")
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private SacredGuide(final SacredGuide card) {
        super(card);
    }

    @Override
    public SacredGuide copy() {
        return new SacredGuide(this);
    }
}
