package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WhisperSquad extends CardImpl {

    private static final FilterCard filter = new FilterCard("a card named Whisper Squad");

    static {
        filter.add(new NamePredicate("Whisper Squad"));
    }

    public WhisperSquad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{B}: Search your library for a card named Whisper Squad, put it onto the battlefield tapped, then shuffle your library.
        this.addAbility(new SimpleActivatedAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(filter), true, true
        ), new ManaCostsImpl<>("{1}{B}")));
    }

    private WhisperSquad(final WhisperSquad card) {
        super(card);
    }

    @Override
    public WhisperSquad copy() {
        return new WhisperSquad(this);
    }
}
