
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
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EchoCircletEffect()));

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

    public EchoCircletEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Equipped creature can block an additional creature each combat";
    }

    public EchoCircletEffect(final EchoCircletEffect effect) {
        super(effect);
    }

    @Override
    public EchoCircletEffect copy() {
        return new EchoCircletEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent perm = game.getPermanent(source.getSourceId());
        if (perm != null && perm.getAttachedTo() != null) {
            Permanent equipped = game.getPermanent(perm.getAttachedTo());
            if (equipped != null) {
                switch (layer) {
                    case RulesEffects:
                        // maxBlocks = 0 equals to "can block any number of creatures"
                        if (equipped.getMaxBlocks() > 0) {
                            equipped.setMaxBlocks(equipped.getMaxBlocks() + 1);
                        }
                        break;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }

}
