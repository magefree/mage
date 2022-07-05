package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrustworthyScout extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named Trustworthy Scout");

    static {
        filter.add(new NamePredicate("Trustworthy Scout"));
    }

    public TrustworthyScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{W}, Exile Trustworthy Scout from your graveyard: Search your library for a card named Trustworthy Scout, reveal it, put it into your hand, then shuffle your library.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new SearchLibraryPutInHandEffect(
                        new TargetCardInLibrary(filter), true
                ), new ManaCostsImpl<>("{1}{W}"));
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private TrustworthyScout(final TrustworthyScout card) {
        super(card);
    }

    @Override
    public TrustworthyScout copy() {
        return new TrustworthyScout(this);
    }
}
