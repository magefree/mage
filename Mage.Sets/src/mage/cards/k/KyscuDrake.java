package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class KyscuDrake extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named Viashivan Dragon");
    private static final FilterControlledCreaturePermanent filterSpitting = new FilterControlledCreaturePermanent("creature named Spitting Drake");

    static {
        filter.add(new NamePredicate("Viashivan Dragon"));
        filterSpitting.add(new NamePredicate("Spitting Drake"));
    }

    public KyscuDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {G}: Kyscu Drake gets +0/+1 until end of turn. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(0, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{G}")));

        // Sacrifice Kyscu Drake and a creature named Spitting Drake: Search your library for a card named Viashivan Dragon and put that card onto the battlefield. Then shuffle your library.
        this.addAbility(new SimpleActivatedAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(1, 1, filter), false, true, Outcome.PutCardInPlay), new CompositeCost(new SacrificeSourceCost(), new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filterSpitting, false)), "sacrifice {this} and a creature named Spitting Drake")));
    }

    private KyscuDrake(final KyscuDrake card) {
        super(card);
    }

    @Override
    public KyscuDrake copy() {
        return new KyscuDrake(this);
    }
}
