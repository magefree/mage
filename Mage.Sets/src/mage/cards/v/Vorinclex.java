package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Vorinclex extends CardImpl {

    private static final FilterCard filter = new FilterCard("Forest cards");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public Vorinclex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.PRAETOR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        this.secondSideCardClazz = mage.cards.t.TheGrandEvolution.class;

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Vorinclex enters the battlefield, search your library for up to two Forest cards, reveal them, put them into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(0, 2, filter), true
        )));

        // {6}{G}{G}: Exile Vorinclex, then return it to the battlefield transformed under its owner's control. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        this.addAbility(new ActivateAsSorceryActivatedAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED),
                new ManaCostsImpl<>("{6}{G}{G}")
        ));
    }

    private Vorinclex(final Vorinclex card) {
        super(card);
    }

    @Override
    public Vorinclex copy() {
        return new Vorinclex(this);
    }
}
