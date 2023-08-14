
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public final class LegionConquistador extends CardImpl {

    private static final FilterCard filter = new FilterCard("cards named Legion Conquistador");

    static {
        filter.add(new NamePredicate("Legion Conquistador"));
    }

    public LegionConquistador(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Legion Conquistador enters the battlefield, you may search your library for any number of cards named Legion Conquistador, reveal them, put them into your hand, then shuffle your library
        TargetCardInLibrary target = new TargetCardInLibrary(0, Integer.MAX_VALUE, filter);
        Effect effect = new SearchLibraryPutInHandEffect(target, true);
        effect.setText("you may search your library for any number of cards named Legion Conquistador, reveal them, put them into your hand, then shuffle");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, true));
    }

    private LegionConquistador(final LegionConquistador card) {
        super(card);
    }

    @Override
    public LegionConquistador copy() {
        return new LegionConquistador(this);
    }
}
