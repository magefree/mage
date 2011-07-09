package mage.abilities.keyword;

import mage.abilities.common.PutIntoGraveFromBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInYourGraveyard;

public class SoulshiftAbility extends PutIntoGraveFromBattlefieldTriggeredAbility {
    private int amount;

    public SoulshiftAbility(int amount) {
        super(new ReturnToHandTargetEffect());
        FilterCard filter = new FilterCard();
        filter.setConvertedManaCost(amount + 1);
        filter.setConvertedManaCostComparison(Filter.ComparisonType.LessThan);
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
    public PutIntoGraveFromBattlefieldTriggeredAbility copy() {
        return new SoulshiftAbility(this);
    }

    @Override
    public String getRule() {
        return "Soulshift " + amount;
    }
}
