package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author weirddan455
 */
public final class GarnaBloodfistOfKeld extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public GarnaBloodfistOfKeld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever another creature you control dies, draw a card if it was attacking. Otherwise, Garna, Bloodfist of Keld deals 1 damage to each opponent.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new ConditionalOneShotEffect(
                        new DrawCardSourceControllerEffect(1),
                        new DamagePlayersEffect(1, TargetController.OPPONENT),
                        GarnaBloodfistOfKeldCondition.instance,
                        "draw a card if it was attacking. Otherwise, {this} deals 1 damage to each opponent"
                ),
                false,
                filter
        ));
    }

    private GarnaBloodfistOfKeld(final GarnaBloodfistOfKeld card) {
        super(card);
    }

    @Override
    public GarnaBloodfistOfKeld copy() {
        return new GarnaBloodfistOfKeld(this);
    }
}

enum GarnaBloodfistOfKeldCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Effects effects = source.getEffects();
        if (!effects.isEmpty()) {
            Object value = effects.get(0).getValue("creatureDied");
            if (value instanceof Permanent) {
                return ((Permanent) value).isAttacking();
            }
        }
        return false;
    }
}
