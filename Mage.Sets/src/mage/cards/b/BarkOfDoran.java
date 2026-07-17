package mage.cards.b;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.ruleModifying.CombatDamageByToughnessAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ToughnessGreaterThanPowerPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BarkOfDoran extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(ToughnessGreaterThanPowerPredicate.instance);
    }

    private static final Condition condition = new AttachedToMatchesFilterCondition(filter);

    public BarkOfDoran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +0/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(0, 1)));

        // As long as equipped creature's toughness is greater than its power, it assigns combat damage equal to its toughness rather than its power.
        this.addAbility(new SimpleStaticAbility(new CombatDamageByToughnessAttachedEffect(
                condition, "as long as equipped creature's toughness is greater than its power, " +
                "it assigns combat damage equal to its toughness rather than its power"
        )));

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private BarkOfDoran(final BarkOfDoran card) {
        super(card);
    }

    @Override
    public BarkOfDoran copy() {
        return new BarkOfDoran(this);
    }
}
