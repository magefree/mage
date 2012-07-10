package mage.abilities.keyword;

import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.target.common.TargetCardInYourGraveyard;

public class SoulshiftAbility extends DiesTriggeredAbility {
    private int amount;

    public SoulshiftAbility(int amount) {
        super(new ReturnToHandTargetEffect());
        FilterCard filter = new FilterCard("Spirit card with converted mana cost " + amount + " or less from your graveyard");
        filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.LessThan, amount + 1));
        filter.getSubtype().add("Spirit");
        filter.setScopeSubtype(Filter.ComparisonScope.Any);
        this.addTarget(new TargetCardInYourGraveyard(filter));
        this.amount = amount;
    }

    public SoulshiftAbility(final SoulshiftAbility ability) {
        super(ability);
        this.amount = ability.amount;
    }

    @Override
    public DiesTriggeredAbility copy() {
        return new SoulshiftAbility(this);
    }

    @Override
    public String getRule() {
        return "Soulshift " + amount;
    }
}
