
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;


/**
 * @author noxx
 */
public final class VanguardsShield extends CardImpl {

    public VanguardsShield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +0/+3 and can block an additional creature each combat.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(0, 3)));

        // Equipped creature can block an additional creature each combat. (static abilit of equipment, no ability that will be gained to equiped creature!)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VanguardsShieldEffect()));

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(3)));
    }

    private VanguardsShield(final VanguardsShield card) {
        super(card);
    }

    @Override
    public VanguardsShield copy() {
        return new VanguardsShield(this);
    }
}

class VanguardsShieldEffect extends ContinuousEffectImpl {

    public VanguardsShieldEffect() {
        super(Duration.WhileOnBattlefield, Outcome.AddAbility);
        staticText = "Equipped creature can block an additional creature each combat";
    }

    private VanguardsShieldEffect(final VanguardsShieldEffect effect) {
        super(effect);
    }

    @Override
    public VanguardsShieldEffect copy() {
        return new VanguardsShieldEffect(this);
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
