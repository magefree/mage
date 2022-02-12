
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author North
 */
public final class HavengulRunebinder extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a creature card from your graveyard");
    private static final FilterControlledCreaturePermanent filterPermanent = new FilterControlledCreaturePermanent("Zombie creature you control");

    static {
        filterPermanent.add(SubType.ZOMBIE.getPredicate());
    }

    public HavengulRunebinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{U}, {tap}, Exile a creature card from your graveyard: Create a 2/2 black Zombie creature token,
        // then put a +1/+1 counter on each Zombie creature you control.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CreateTokenEffect(new ZombieToken()),
                new ManaCostsImpl("{2}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(filter)));
        ability.addEffect(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filterPermanent).concatBy(", then"));
        this.addAbility(ability);
    }

    private HavengulRunebinder(final HavengulRunebinder card) {
        super(card);
    }

    @Override
    public HavengulRunebinder copy() {
        return new HavengulRunebinder(this);
    }
}
