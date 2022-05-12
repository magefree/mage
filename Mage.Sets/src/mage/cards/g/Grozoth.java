package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TransmuteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class Grozoth extends CardImpl {

    private static final FilterCard filter = new FilterCard("cards that have mana value 9");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, 9));
    }

    public Grozoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}{U}{U}");
        this.subtype.add(SubType.LEVIATHAN);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // When Grozoth enters the battlefield, you may search your library for any number of cards that have converted mana cost 9, reveal them, and put them into your hand. If you do, shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(0, Integer.MAX_VALUE, filter), true, true
        ), true));

        // {4}: Grozoth loses defender until end of turn.
        this.addAbility(new SimpleActivatedAbility(new LoseAbilitySourceEffect(
                DefenderAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{4}")));

        // Transmute {1}{U}{U}
        this.addAbility(new TransmuteAbility("{1}{U}{U}"));
    }

    private Grozoth(final Grozoth card) {
        super(card);
    }

    @Override
    public Grozoth copy() {
        return new Grozoth(this);
    }
}
