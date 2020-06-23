
package mage.cards.h;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandPermanent;

/**
 *
 * @author fireshoes
 */
public final class HourOfRevelation extends CardImpl {

    public HourOfRevelation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}{W}");

        // Hour of Revelation costs {3} less to cast if there are ten or more nonland permanents on the battlefield.
        SimpleStaticAbility ability = new SimpleStaticAbility(Zone.ALL,
                new SpellCostReductionSourceEffect(3, new PermanentsOnTheBattlefieldCondition(
                        new FilterNonlandPermanent("there are ten or more nonland permanents on the battlefield"), ComparisonType.MORE_THAN, 9, false)));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Destroy all nonland permanents.
        this.getSpellAbility().addEffect(new DestroyAllEffect(new FilterNonlandPermanent("nonland permanents")));

    }

    public HourOfRevelation(final HourOfRevelation card) {
        super(card);
    }

    @Override
    public HourOfRevelation copy() {
        return new HourOfRevelation(this);
    }
}
