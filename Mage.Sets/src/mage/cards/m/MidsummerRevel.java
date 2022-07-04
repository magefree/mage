
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.token.BeastToken;

/**
 *
 * @author LoneFox
 */
public final class MidsummerRevel extends CardImpl {

    public MidsummerRevel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{G}{G}");

        // At the beginning of your upkeep, you may put a verse counter on Midsummer Revel.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
            new AddCountersSourceEffect(CounterType.VERSE.createInstance(), true), TargetController.YOU, true));
        // {G}, Sacrifice Midsummer Revel: create X 3/3 green Beast creature tokens, where X is the number of verse counters on Midsummer Revel.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new BeastToken(),
            new CountersSourceCount(CounterType.VERSE)), new ManaCostsImpl<>("{G}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private MidsummerRevel(final MidsummerRevel card) {
        super(card);
    }

    @Override
    public MidsummerRevel copy() {
        return new MidsummerRevel(this);
    }
}
