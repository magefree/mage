package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.AbilitiesCostReductionControllerEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BureauHeadmaster extends CardImpl {

    private static final FilterCard filter = new FilterCard("Equipment spells");

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
    }

    public BureauHeadmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Equipment spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // Equip abilities you activate cost {1} less to activate.
        this.addAbility(new SimpleStaticAbility(new AbilitiesCostReductionControllerEffect(
                EquipAbility.class, "Equip", 1
        ).setText("equip abilities you activate cost {1} less to activate")));
    }

    private BureauHeadmaster(final BureauHeadmaster card) {
        super(card);
    }

    @Override
    public BureauHeadmaster copy() {
        return new BureauHeadmaster(this);
    }
}
