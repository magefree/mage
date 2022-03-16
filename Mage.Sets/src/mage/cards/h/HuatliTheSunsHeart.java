package mage.cards.h;

import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.GreatestToughnessAmongControlledCreaturesValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ruleModifying.CombatDamageByToughnessEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HuatliTheSunsHeart extends CardImpl {

    public HuatliTheSunsHeart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{G/W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUATLI);
        this.setStartingLoyalty(7);

        // Each creature you control assigns combat damage equal to its toughness rather than its power.
        this.addAbility(new SimpleStaticAbility(new CombatDamageByToughnessEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE, true
        )));

        // -3: You gain life equal to the greatest toughness among creatures you control.
        this.addAbility(new LoyaltyAbility(new GainLifeEffect(
                GreatestToughnessAmongControlledCreaturesValue.instance,
                "You gain life equal to the greatest toughness among creatures you control"
        ), -3));
    }

    private HuatliTheSunsHeart(final HuatliTheSunsHeart card) {
        super(card);
    }

    @Override
    public HuatliTheSunsHeart copy() {
        return new HuatliTheSunsHeart(this);
    }
}
