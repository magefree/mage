
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class TerraRavager extends CardImpl {

    public TerraRavager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Whenever Terra Ravager attacks, it gets +X/+0 until end of turn, where X is the number of lands defending player controls.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(new TerraRavagerLandCount(), StaticValue.get(0), Duration.EndOfTurn, true), false));
    }

    private TerraRavager(final TerraRavager card) {
        super(card);
    }

    @Override
    public TerraRavager copy() {
        return new TerraRavager(this);
    }
}

class TerraRavagerLandCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        for (CombatGroup group :game.getCombat().getGroups()) {
            if (group.getAttackers().contains(sourceAbility.getSourceId())) {
                UUID defenderId = group.getDefenderId();
                if (group.isDefenderIsPlaneswalker()) {
                    Permanent permanent = game.getPermanent(defenderId);
                    if (permanent != null) {
                        defenderId = permanent.getControllerId();
                    }
                }
                return game.getBattlefield().countAll(new FilterLandPermanent(), defenderId, game);

            }
        }
        return 0;
    }

    @Override
    public TerraRavagerLandCount copy() {
        return new TerraRavagerLandCount();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "the number of lands defending player controls";
    }
}
