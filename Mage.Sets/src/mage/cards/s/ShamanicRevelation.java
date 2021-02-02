package mage.cards.s;

import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ShamanicRevelation extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public ShamanicRevelation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");

        // Draw a card for each creature you control.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURE)));

        // <i>Ferocious</i> &mdash; You gain 4 life for each creature you control with power 4 or greater.
        DynamicValue amount = new PermanentsOnBattlefieldCount(filter, 4);
        Effect effect = new GainLifeEffect(amount);
        effect.setText("<br><i>Ferocious</i> &mdash; You gain 4 life for each creature you control with power 4 or greater.");
        this.getSpellAbility().addEffect(effect);
    }

    private ShamanicRevelation(final ShamanicRevelation card) {
        super(card);
    }

    @Override
    public ShamanicRevelation copy() {
        return new ShamanicRevelation(this);
    }
}
