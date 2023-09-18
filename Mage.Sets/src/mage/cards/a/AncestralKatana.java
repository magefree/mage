package mage.cards.a;

import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AncestralKatana extends CardImpl {

    public AncestralKatana(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Whenever a Samurai or Warrior you control attacks alone, you may pay {1}. When you do, attach Ancestral Katana to it.
        this.addAbility(new AttacksAloneControlledTriggeredAbility(new DoWhenCostPaid(
                new ReflexiveTriggeredAbility(
                        new AttachEffect(Outcome.BoostCreature),
                        false, "attach {this} to it"
                ), new GenericManaCost(1), "Pay {1}?"
        ), StaticFilters.FILTER_CONTROLLED_SAMURAI_OR_WARRIOR, true, false));

        // Equipped creature gets +2/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 1)));

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private AncestralKatana(final AncestralKatana card) {
        super(card);
    }

    @Override
    public AncestralKatana copy() {
        return new AncestralKatana(this);
    }
}
