package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.mana.AddManaOfAnyTypeProducedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KinnanBonderProdigy extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("you tap a nonland permanent");
    private static final FilterCard filter2 = new FilterCreatureCard("non-Human creature card");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter2.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    public KinnanBonderProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you tap a nonland permanent for mana, add one mana of any type that permanent produced.
        AddManaOfAnyTypeProducedEffect effect = new AddManaOfAnyTypeProducedEffect();
        effect.setText("add one mana of any type that permanent produced");
        this.addAbility(new TapForManaAllTriggeredManaAbility(
                effect, filter, SetTargetPointer.PERMANENT
        ));

        // {5}{G}{U}: Look at the top five cards of your library.
        // You may put a non-Human creature card from among them onto the battlefield.
        // Put the rest on the bottom of your library in a random order.
        this.addAbility(new SimpleActivatedAbility(
                new LookLibraryAndPickControllerEffect(5, 1, filter2, PutCards.BATTLEFIELD, PutCards.BOTTOM_RANDOM),
                new ManaCostsImpl<>("{5}{G}{U}")));
    }

    private KinnanBonderProdigy(final KinnanBonderProdigy card) {
        super(card);
    }

    @Override
    public KinnanBonderProdigy copy() {
        return new KinnanBonderProdigy(this);
    }
}
