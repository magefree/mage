package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterUntappedCreature;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author NinthWorld
 */
public final class Overlord extends CardImpl {

    private static final FilterCard filter = new FilterCard("Zerg creature spells");
    private static final FilterControlledPermanent filterAnother = new FilterControlledPermanent("another untapped creature you control");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new SubtypePredicate(SubType.ZERG));
        filterAnother.add(Predicates.not(new TappedPredicate()));
        filterAnother.add(new AnotherPredicate());
    }

    public Overlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        
        this.subtype.add(SubType.ZERG);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Zerg creature spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));

        // Tap another untapped creature you control: Overlord gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn),
                new TapTargetCost(new TargetControlledPermanent(filterAnother))));
    }

    public Overlord(final Overlord card) {
        super(card);
    }

    @Override
    public Overlord copy() {
        return new Overlord(this);
    }
}
