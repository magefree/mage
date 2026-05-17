
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;


/**
 * @author nantuko
 */
public final class EchoCirclet extends CardImpl {

    public EchoCirclet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature can block an additional creature each combat. (static abilit of equipment, no ability that will be gained to equiped creature!)
        this.addAbility(new SimpleStaticAbility(new EchoCircletEffect()));

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1), new TargetControlledCreaturePermanent(), false));
    }

    private EchoCirclet(final EchoCirclet card) {
        super(card);
    }

    @Override
    public EchoCirclet copy() {
        return new EchoCirclet(this);
    }
}

class EchoCircletEffect extends ContinuousEffectImpl {

    EchoCircletEffect() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "Equipped creature can block an additional creature each combat";
    }

    private EchoCircletEffect(final EchoCircletEffect effect) {
        super(effect);
    }

    @Override
    public EchoCircletEffect copy() {
        return new EchoCircletEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getPermanentSourceAttachedToIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        // maxBlocks = 0 equals to "can block any number of creatures"
        if (permanent.getMaxBlocks() > 0) {
            permanent.setMaxBlocks(permanent.getMaxBlocks() + 1);
        }
        return true;
    }

}
