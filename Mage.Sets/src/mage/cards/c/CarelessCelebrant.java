package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CarelessCelebrant extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("creature or planeswalker an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public CarelessCelebrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.SATYR);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Careless Celebrant dies, it deals 2 damage to target creature or planeswalker an opponent controls.
        Ability ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(2, "it"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private CarelessCelebrant(final CarelessCelebrant card) {
        super(card);
    }

    @Override
    public CarelessCelebrant copy() {
        return new CarelessCelebrant(this);
    }
}
