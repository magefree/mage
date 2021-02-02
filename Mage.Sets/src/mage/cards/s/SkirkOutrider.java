
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author Wehk
 */
public final class SkirkOutrider extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Beast");

    static {
        filter.add(SubType.BEAST.getPredicate());
    }    
    
    public SkirkOutrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As long as you control a Beast, Skirk Outrider gets +2/+2 and has trample.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield),
            new PermanentsOnTheBattlefieldCondition(filter),
            "As long as you control a Beast, {this} gets +2/+2"));
        ability.addEffect(new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(TrampleAbility.getInstance()),
            new PermanentsOnTheBattlefieldCondition(filter),
            "and has trample"));
        this.addAbility(ability);
    }

    private SkirkOutrider(final SkirkOutrider card) {
        super(card);
    }

    @Override
    public SkirkOutrider copy() {
        return new SkirkOutrider(this);
    }
}
