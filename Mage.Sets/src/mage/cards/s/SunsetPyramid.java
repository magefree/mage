
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 *
 * @author spjspj
 */
public final class SunsetPyramid extends CardImpl {

    public SunsetPyramid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Sunset Pyramid enters the battlefield with three brick counters on it.
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.BRICK.createInstance(3)));

        // {2}, {T}, Remove a brick counter from Sunset Pyramid: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.BRICK.createInstance()));
        this.addAbility(ability);

        // {2}, {T}: Scry 1.
        Ability ability2 = new SimpleActivatedAbility(new ScryEffect(1, false), new GenericManaCost(2));
        ability2.addCost(new TapSourceCost());
        this.addAbility(ability2);

    }

    private SunsetPyramid(final SunsetPyramid card) {
        super(card);
    }

    @Override
    public SunsetPyramid copy() {
        return new SunsetPyramid(this);
    }
}
