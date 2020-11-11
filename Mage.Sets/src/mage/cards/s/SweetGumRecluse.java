package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SweetGumRecluse extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creatures that entered the battlefield this turn");

    static {
        filter.add(EnteredThisTurnPredicate.instance);
    }

    public SweetGumRecluse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Cascade
        this.addAbility(new CascadeAbility());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Sweet-Gum Recluse enters the battlefield, put three +1/+1 counters on each of any number of target creatures that entered the battlefield this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(3))
                        .setText("put three +1/+1 counters on each of any number of target creatures that entered the battlefield this turn")
        );
        ability.addTarget(new TargetPermanent(0, Integer.MAX_VALUE, filter, false));
        this.addAbility(ability);
    }

    private SweetGumRecluse(final SweetGumRecluse card) {
        super(card);
    }

    @Override
    public SweetGumRecluse copy() {
        return new SweetGumRecluse(this);
    }
}
