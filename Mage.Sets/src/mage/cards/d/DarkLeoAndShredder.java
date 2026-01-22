package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseHalfLifeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.SneakAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.NinjaToken3;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DarkLeoAndShredder extends CardImpl {

    private static final FilterPermanent filter = new FilterAttackingCreature("attacking Ninjas");

    static {
        filter.add(SubType.NINJA.getPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.NINJA), ComparisonType.OR_GREATER, 5
    );
    private static final Hint hint = new ValueHint(
            "Ninjas you control", new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.NINJA))
    );

    public DarkLeoAndShredder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Sneak {W}{B}
        this.addAbility(new SneakAbility(this, "{W}{B}"));

        // Attacking Ninjas you control have deathtouch.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));

        // Whenever Dark Leo & Shredder deal combat damage to a player, create a 1/1 black Ninja creature token. Then if you control five or more Ninjas, that player loses half their life, rounded up.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new CreateTokenEffect(new NinjaToken3()), false, true
        ).setTriggerPhrase("Whenever {this} deal combat damage to a player, ");
        ability.addEffect(new ConditionalOneShotEffect(
                new LoseHalfLifeTargetEffect(), condition, "Then if you control " +
                "five or more Ninjas, that player loses half their life, rounded up"
        ));
        this.addAbility(ability.addHint(hint));
    }

    private DarkLeoAndShredder(final DarkLeoAndShredder card) {
        super(card);
    }

    @Override
    public DarkLeoAndShredder copy() {
        return new DarkLeoAndShredder(this);
    }
}
