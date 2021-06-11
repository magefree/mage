package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.permanent.token.PhyrexianMyrToken;

import java.util.UUID;

/**
 * @author North
 */
public final class ShrineOfLoyalLegions extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a white spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public ShrineOfLoyalLegions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        //At the beginning of your upkeep or whenever you cast a white spell, put a charge counter on Shrine of Loyal Legions.
        this.addAbility(new OrTriggeredAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance()),
                new BeginningOfUpkeepTriggeredAbility(null, TargetController.YOU, false),
                new SpellCastControllerTriggeredAbility(null, filter, false)));

        //{3}, {T}, Sacrifice Shrine of Loyal Legions: Create a 1/1 colorless Myr artifact creature token for each charge counter on Shrine of Loyal Legions.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CreateTokenEffect(new PhyrexianMyrToken(), new CountersSourceCount(CounterType.CHARGE)),
                new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private ShrineOfLoyalLegions(final ShrineOfLoyalLegions card) {
        super(card);
    }

    @Override
    public ShrineOfLoyalLegions copy() {
        return new ShrineOfLoyalLegions(this);
    }
}
