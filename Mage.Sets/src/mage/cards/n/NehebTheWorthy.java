
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class NehebTheWorthy extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Minotaurs");

    static {
        filter.add(SubType.MINOTAUR.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public NehebTheWorthy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Other Minotaurs you control have first strike.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter, true)));

        // As long as you have one or fewer cards in hand, Minotaurs you control get +2/+0.
        Condition condition = new CardsInHandCondition(ComparisonType.FEWER_THAN, 2);
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostControlledEffect(2, 0, Duration.WhileOnBattlefield, filter), condition, "As long as you have one or fewer cards in hand, Minotaurs you control get +2/+0"));
        this.addAbility(ability);

        // Whenever Neheb, the Worthy deals combat damage to a player, each player discards a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DiscardEachPlayerEffect(), false));
    }

    private NehebTheWorthy(final NehebTheWorthy card) {
        super(card);
    }

    @Override
    public NehebTheWorthy copy() {
        return new NehebTheWorthy(this);
    }
}
