package mage.cards.h;

import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HauntedRidge extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_LANDS, ComparisonType.FEWER_THAN, 2
    );

    public HauntedRidge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Haunted Ridge enters the battlefield tapped unless you control two or more other lands.
        this.addAbility(new EntersBattlefieldAbility(
                new ConditionalOneShotEffect(new TapSourceEffect(), condition),
                "tapped unless you control two or more other lands"
        ));

        // {T}: Add {B} or {R}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new BlueManaAbility());
    }

    private HauntedRidge(final HauntedRidge card) {
        super(card);
    }

    @Override
    public HauntedRidge copy() {
        return new HauntedRidge(this);
    }
}
