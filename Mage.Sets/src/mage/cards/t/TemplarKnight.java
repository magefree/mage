package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TemplarKnight extends CardImpl {

    private static final FilterCard filter = new FilterArtifactCard("a legendary artifact card");
    private static final FilterControlledPermanent filter2
            = new FilterControlledCreaturePermanent("untapped attacking creatures you control named Templar Knight");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter2.add(TappedPredicate.UNTAPPED);
        filter2.add(AttackingPredicate.instance);
        filter2.add(new NamePredicate("Templar Knight"));
    }

    public TemplarKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {W}, Tap five untapped attacking creatures you control named Templar Knight: Search your library for a legendary artifact card, put it onto the battlefield, then shuffle.
        Ability ability = new SimpleActivatedAbility(
                new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)), new ManaCostsImpl<>("{W}")
        );
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(5, filter2)));
        this.addAbility(ability);

        // A deck can have any number of cards named Templar Knight.
        this.addAbility(new SimpleStaticAbility(new InfoEffect(
                "a deck can have any number of cards named Templar Knight"
        )));
    }

    private TemplarKnight(final TemplarKnight card) {
        super(card);
    }

    @Override
    public TemplarKnight copy() {
        return new TemplarKnight(this);
    }
}
