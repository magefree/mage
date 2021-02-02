
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class RenegadeFirebrand extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();
    private static final String rule = "As long as you control a Chandra planeswalker, {this} gets +1/+0";

    static {
        filter.add(CardType.PLANESWALKER.getPredicate());
        filter.add(SubType.CHANDRA.getPredicate());
    }

    public RenegadeFirebrand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // As long as you control a Chandra planeswalker, Renegade Firebrand gets +1/+0 and has first strike.
        ConditionalContinuousEffect effect1 = new ConditionalContinuousEffect(new BoostSourceEffect(1, 0, Duration.WhileOnBattlefield), new PermanentsOnTheBattlefieldCondition(filter), rule);
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect1);
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield), new PermanentsOnTheBattlefieldCondition(filter), "and has first strike"));
        this.addAbility(ability);
    }

    private RenegadeFirebrand(final RenegadeFirebrand card) {
        super(card);
    }

    @Override
    public RenegadeFirebrand copy() {
        return new RenegadeFirebrand(this);
    }
}
