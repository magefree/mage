
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jeffwadsworth
 */
public final class MwonvuliBeastTracker extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("creature card with deathtouch, hexproof, reach, or trample");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(Predicates.or(
        new AbilityPredicate(DeathtouchAbility.class),
        new AbilityPredicate(HexproofAbility.class),
        new AbilityPredicate(ReachAbility.class),
        new AbilityPredicate(TrampleAbility.class)));
    }

    public MwonvuliBeastTracker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Mwonvuli Beast Tracker enters the battlefield, search your library for a creature card with deathtouch, hexproof, reach, or trample and reveal it. Shuffle your library and put that card on top of it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(filter), true)));
    }

    private MwonvuliBeastTracker(final MwonvuliBeastTracker card) {
        super(card);
    }

    @Override
    public MwonvuliBeastTracker copy() {
        return new MwonvuliBeastTracker(this);
    }
}
