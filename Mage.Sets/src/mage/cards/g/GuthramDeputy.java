package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 *
 * @author EikePeace
 */
public final class GuthramDeputy extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Equipment or Aura card");

    static {
        filter.add(Predicates.or(
                new SubtypePredicate(SubType.EQUIPMENT),
                new SubtypePredicate(SubType.AURA)
        ));
    }

    public GuthramDeputy(UUID ownerId, CardSetInfo setInfo){
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        //When Guthram Deputy enters the battlefield, search your library for an Equipment or Aura card, reveal it, and put it into your hand. Then shuffle your library.
            TargetCardInLibrary target = new TargetCardInLibrary(1, 1, filter);
            this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(target, true, true), false));
    }


    public GuthramDeputy(final GuthramDeputy card) {
        super(card);
    }

    @Override
    public GuthramDeputy copy() {
        return new GuthramDeputy(this);
    }
}
