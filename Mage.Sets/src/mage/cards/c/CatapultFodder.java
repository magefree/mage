package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author weirddan455
 */
public final class CatapultFodder extends CardImpl {

    public CatapultFodder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        this.transformable = true;
        this.secondSideCardClazz = mage.cards.c.CatapultCaptain.class;

        // At the beginning of combat on your turn, if you control three or more creatures that each have toughness greater than their power, transform Catapult Fodder.
        this.addAbility(new TransformAbility());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(new TransformSourceEffect(true, true), TargetController.YOU, false),
                CatapultFodderCondition.instance,
                "At the beginning of combat on your turn, if you control three or more creatures that each have toughness greater than their power, transform {this}"
        ));
    }

    private CatapultFodder(final CatapultFodder card) {
        super(card);
    }

    @Override
    public CatapultFodder copy() {
        return new CatapultFodder(this);
    }
}

enum CatapultFodderCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        int creatures = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (permanent.isCreature(game) && permanent.getToughness().getValue() > permanent.getPower().getValue()) {
                creatures++;
                if (creatures >= 3) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "you control three or more creatures that each have toughness greater than their power";
    }
}
