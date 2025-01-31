package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GasGuzzler extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another creature or Vehicle");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public GasGuzzler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // This creature enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Max speed -- {B}, Sacrifice another creature or Vehicle: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{B}")
        );
        ability.addCost(new SacrificeTargetCost(filter));
        this.addAbility(new MaxSpeedAbility(ability));
    }

    private GasGuzzler(final GasGuzzler card) {
        super(card);
    }

    @Override
    public GasGuzzler copy() {
        return new GasGuzzler(this);
    }
}
