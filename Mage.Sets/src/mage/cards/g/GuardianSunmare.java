package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.AttacksWhileSaddledTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.SaddleAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuardianSunmare extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard("nonland permanent card with mana value 3 or less");

    static {
        filter.add(PermanentPredicate.instance);
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public GuardianSunmare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.HORSE);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // Whenever this creature attacks while saddled, search your library for a nonland permanent card with mana value 3 or less, put it onto the battlefield, then shuffle.
        this.addAbility(new AttacksWhileSaddledTriggeredAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter))
        ));

        // Saddle 4
        this.addAbility(new SaddleAbility(4));
    }

    private GuardianSunmare(final GuardianSunmare card) {
        super(card);
    }

    @Override
    public GuardianSunmare copy() {
        return new GuardianSunmare(this);
    }
}
