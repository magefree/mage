package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.EquipmentAttachedCount;
import mage.abilities.effects.common.AttachToSourceTargetEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShagratLootBearer extends CardImpl {

    public ShagratLootBearer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Shagrat, Loot Bearer attacks, attach up to one target Equipment to it. Then amass Orcs X, where X is the number of Equipment attached to Shagrat.
        Ability ability = new AttacksTriggeredAbility(
                new AttachToSourceTargetEffect().setText("attach up to one target Equipment to it")
        );
        ability.addEffect(new AmassEffect(new EquipmentAttachedCount(), SubType.ORC, false)
                .setText("Then amass Orcs X, where X is the number of Equipment attached to {this}"));
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_EQUIPMENT));
        this.addAbility(ability);
    }

    private ShagratLootBearer(final ShagratLootBearer card) {
        super(card);
    }

    @Override
    public ShagratLootBearer copy() {
        return new ShagratLootBearer(this);
    }
}
