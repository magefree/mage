package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.HistoricPredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MonumentalHenge extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.PLAINS);
    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    private static final FilterCard filterCard = new FilterCard("a historic card");

    static {
        filterCard.add(HistoricPredicate.instance);
    }

    public MonumentalHenge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Monumental Henge enters the battlefield tapped unless you control a Plains.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // {2}{W}{W}, {T}: Look at the top five cards of your library. You may reveal a historic card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        Ability ability = new SimpleActivatedAbility(
                new LookLibraryAndPickControllerEffect(5, 1, filterCard, PutCards.HAND, PutCards.BOTTOM_RANDOM, true),
                new ManaCostsImpl<>("{2}{W}{W}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private MonumentalHenge(final MonumentalHenge card) {
        super(card);
    }

    @Override
    public MonumentalHenge copy() {
        return new MonumentalHenge(this);
    }
}
