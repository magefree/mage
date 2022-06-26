
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
 
/**
 *
 * @author LevelX2
 */
public final class CivicSaber extends CardImpl {
 
    public CivicSaber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);
 
        // Equipped creature gets +1/+0 for each of its colors.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(new CivicSaberColorCount(), StaticValue.get(0), Duration.WhileOnBattlefield)));

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(1), new TargetControlledCreaturePermanent(), false));
    }
 
    private CivicSaber(final CivicSaber card) {
        super(card);
    }
 
    @Override
    public CivicSaber copy() {
        return new CivicSaber(this);
    }
}
 
class CivicSaberColorCount implements DynamicValue {
 
    public CivicSaberColorCount() {
    }
 
    public CivicSaberColorCount(final CivicSaberColorCount dynamicValue) {
    }
 
    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        Permanent equipment = game.getPermanent(sourceAbility.getSourceId());
        if (equipment != null) {
            Permanent permanent = game.getPermanent(equipment.getAttachedTo());
            if (permanent != null) {
                count = permanent.getColor(game).getColorCount();
            }
        }
        return count;
    }
 
    @Override
    public DynamicValue copy() {
        return new CivicSaberColorCount(this);
    }
 
    @Override
    public String toString() {
        return "1";
    }
 
    @Override
    public String getMessage() {
        return "of its colors";
    }
}
 