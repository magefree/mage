

package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class RangerOfEos extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature cards with mana value 1 or less");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 2));
    }

    public RangerOfEos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Ranger of Eos enters the battlefield, you may search your library for up to two creature cards with converted mana cost 1 or less,
        // reveal them, and put them into your hand. If you do, shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(0, 2, filter);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(target, true), true));
    }

    private RangerOfEos(final RangerOfEos card) {
        super(card);
    }

    @Override
    public RangerOfEos copy() {
        return new RangerOfEos(this);
    }

}
