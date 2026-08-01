package mage.cards.c;

import java.util.UUID;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CrudeBentBlade extends CardImpl {

    public CrudeBentBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, target opponent sacrifices a creature of their choice.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SacrificeEffect(
            StaticFilters.FILTER_PERMANENT_CREATURE, 1, "target opponent"
        ));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Equipped creature gets +2/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 1)));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private CrudeBentBlade(final CrudeBentBlade card) {
        super(card);
    }

    @Override
    public CrudeBentBlade copy() {
        return new CrudeBentBlade(this);
    }
}
