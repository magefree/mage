
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class ImperialRecruiter extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card with power 2 or less");

    static{
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }
    
    public ImperialRecruiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Imperial Recruiter enters the battlefield, search your library for a creature card with power 2 or less, reveal it, and put it into your hand. Then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)));
    }

    private ImperialRecruiter(final ImperialRecruiter card) {
        super(card);
    }

    @Override
    public ImperialRecruiter copy() {
        return new ImperialRecruiter(this);
    }
}
