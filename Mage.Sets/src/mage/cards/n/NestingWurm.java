

package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author L_J (based on BetaSteward_at_googlemail.com)
 */
public final class NestingWurm extends CardImpl {

    private static final FilterCard filter = new FilterCard("cards named Nesting Wurm");

    static {
        filter.add(new NamePredicate("Nesting Wurm"));
    }

    public NestingWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Nesting Wurm enters the battlefield, you may search your library for up to three cards named Nesting Wurm, reveal them, and put them into your hand. If you do, shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(0, 3, filter);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(target, true), true));
    }

    private NestingWurm(final NestingWurm card) {
        super(card);
    }

    @Override
    public NestingWurm copy() {
        return new NestingWurm(this);
    }

}
