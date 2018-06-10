
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.costs.AdjustingSourceCosts;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.util.CardUtil;
import mage.watchers.common.MorbidWatcher;

/**
 *
 * @author stravant
 */
public final class BonePicker extends CardImpl {

    public BonePicker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Bone Picker costs {3} less to cast if a creature died this turn.
        this.addAbility(new BonePickerCostAdjustmentAbility(), new MorbidWatcher());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

    }

    public BonePicker(final BonePicker card) {
        super(card);
    }

    @Override
    public BonePicker copy() {
        return new BonePicker(this);
    }
}

class BonePickerCostAdjustmentAbility extends SimpleStaticAbility implements AdjustingSourceCosts {

    public BonePickerCostAdjustmentAbility() {
        super(Zone.OUTSIDE, null);
    }

    public BonePickerCostAdjustmentAbility(final BonePickerCostAdjustmentAbility ability) {
        super(ability);
    }

    @Override
    public SimpleStaticAbility copy() {
        return new BonePickerCostAdjustmentAbility(this);
    }

    @Override
    public String getRule() {
        return "If a creature died this turn, {this} costs {3} less to cast.";
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (ability instanceof SpellAbility) { // Prevent adjustment of activated ability
            if (MorbidCondition.instance.apply(game, ability)) {
                CardUtil.adjustCost((SpellAbility) ability, 3);
            }
        }
    }
}
