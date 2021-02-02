package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JacesSentinel extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Jace planeswalker");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(CardType.PLANESWALKER.getPredicate());
        filter.add(SubType.JACE.getPredicate());
    }

    public JacesSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // As long as you control a Jace planeswalker, Jace's Sentinel gets +1/+0 and can't be blocked.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield),
                new PermanentsOnTheBattlefieldCondition(filter),
                "As long as you control a Jace planeswalker, {this} gets +1/+0"));
        ability.addEffect(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(),
                new PermanentsOnTheBattlefieldCondition(filter),
                "and can't be blocked"));
        this.addAbility(ability);
    }

    private JacesSentinel(final JacesSentinel card) {
        super(card);
    }

    @Override
    public JacesSentinel copy() {
        return new JacesSentinel(this);
    }
}
