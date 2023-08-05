package mage.cards.i;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfTheros extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Aura, God, or Demigod card");

    static {
        filter.add(Predicates.or(
                SubType.AURA.getPredicate(),
                SubType.GOD.getPredicate(),
                SubType.DEMIGOD.getPredicate()
        ));
    }

    public InvasionOfTheros(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{2}{W}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(4);
        this.secondSideCardClazz = mage.cards.e.EpharaEverSheltering.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SiegeAbility());

        // When Invasion of Theros enters the battlefield, search your library for an Aura, God, or Demigod card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)
        ));
    }

    private InvasionOfTheros(final InvasionOfTheros card) {
        super(card);
    }

    @Override
    public InvasionOfTheros copy() {
        return new InvasionOfTheros(this);
    }
}
