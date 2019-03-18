

package mage.abilities.keyword;

import mage.constants.ComparisonType;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 *
 *  702.45. Soulshift
 *      702.45a Soulshift is a triggered ability. “Soulshift N” means “When this permanent is put into a graveyard from play,
 *              you may return target Spirit card with converted mana cost N or less from your graveyard to your hand.”
 *      702.45b If a permanent has multiple instances of soulshift, each triggers separately.
 *
 *  The soulshift number tells you the maximum converted mana cost of the Spirit card you can target.
 *  You choose whether or not to return the targeted creature card when the ability resolves.
 *
 * @author Loki, LevelX2
 */
public class SoulshiftAbility extends DiesTriggeredAbility {
    
    private final DynamicValue amount;

    public SoulshiftAbility(int amount) {
        this(new StaticValue(amount));
    }

    public SoulshiftAbility(DynamicValue amount) {
        super(new ReturnToHandTargetEffect());
        this.amount = amount;        
    }

    public SoulshiftAbility(final SoulshiftAbility ability) {
        super(ability);
        this.amount = ability.amount;
    }

    @Override
    public void trigger(Game game, UUID controllerId) {
        this.getTargets().clear();
        int intValue = amount.calculate(game, this, null);
        FilterCard filter = new FilterCard("Spirit card with converted mana cost " + intValue + " or less from your graveyard");
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN,  intValue + 1));
        filter.add(new SubtypePredicate(SubType.SPIRIT));
        this.addTarget(new TargetCardInYourGraveyard(filter));
        super.trigger(game, controllerId); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DiesTriggeredAbility copy() {
        return new SoulshiftAbility(this);
    }

    @Override
    public String getRule() {
        if (amount instanceof StaticValue) {
            return "Soulshift " + amount.toString() + " <i>(When this creature dies, you may return target Spirit card with converted mana cost " + amount.toString() + " or less from your graveyard to your hand.)</i>";
        } else {
            return "{this} has soulshift X, where X is the number of " + amount.getMessage() +
            ". <i>(When this creature dies, you may return target Spirit card with converted mana cost X or less from your graveyard to your hand.)</i>";
        }
        
    }
}
