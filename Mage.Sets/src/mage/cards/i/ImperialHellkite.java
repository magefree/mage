package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class ImperialHellkite extends CardImpl {

    private static final FilterCard filter = new FilterCard(SubType.DRAGON);

    public ImperialHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Morph {6}{R}{R}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl<>("{6}{R}{R}")));

        // When Imperial Hellkite is turned face up, you may search your library for a Dragon card, reveal it, and put it into your hand. If you do, shuffle your library.
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), false, true
        ));
    }

    private ImperialHellkite(final ImperialHellkite card) {
        super(card);
    }

    @Override
    public ImperialHellkite copy() {
        return new ImperialHellkite(this);
    }
}
