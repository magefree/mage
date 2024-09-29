package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SanguinaryPriest extends CardImpl {

    public SanguinaryPriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ASTARTES);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Blood Chalice -- Whenever another creature you control dies, Sanguinary Priest deals 1 damage to any target.
        Ability ability = new DiesCreatureTriggeredAbility(new DamageTargetEffect(1), false, StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability.withFlavorWord("Blood Chalice"));
    }

    private SanguinaryPriest(final SanguinaryPriest card) {
        super(card);
    }

    @Override
    public SanguinaryPriest copy() {
        return new SanguinaryPriest(this);
    }
}
