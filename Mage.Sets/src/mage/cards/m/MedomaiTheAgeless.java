
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CantAttackAnyPlayerSourceEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public final class MedomaiTheAgeless extends CardImpl {

    public MedomaiTheAgeless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Medomai the Ageless deals combat damage to a player, take an extra turn after this one.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new AddExtraTurnControllerEffect(), false));
        // Medomai the Ageless can't attack during extra turns.
        Effect effect = new ConditionalRestrictionEffect(new CantAttackAnyPlayerSourceEffect(Duration.WhileOnBattlefield), ExtraTurnCondition.instance);
        effect.setText("{this} can't attack during extra turns");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private MedomaiTheAgeless(final MedomaiTheAgeless card) {
        super(card);
    }

    @Override
    public MedomaiTheAgeless copy() {
        return new MedomaiTheAgeless(this);
    }
}

enum ExtraTurnCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getState().isExtraTurn();
    }
}
