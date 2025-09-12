
package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.MoveCounterTargetsEffect;
import mage.abilities.keyword.BountyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class RumorMonger extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another target creature");

    static {
        filter.add(new AnotherTargetPredicate(2));
    }

    public RumorMonger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}{G}");
        this.subtype.add(SubType.ARCONA);
        this.subtype.add(SubType.HUNTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Rumor Monger enters the battlefield, put a bounty counter on up to two target creatures an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()));
        ability.addTarget(new TargetOpponentsCreaturePermanent(0, 2));
        this.addAbility(ability);

        // <i>Bounty</i> &mdash; Whenever a creature an opponent controls with a bounty counter on it dies, you may move a bounty counter from one target creature to another target creatute.
        ability = new BountyAbility(new MoveCounterTargetsEffect(CounterType.BOUNTY), true);
        ability.addTarget(new TargetCreaturePermanent().withChooseHint("to remove a counter from").setTargetTag(1));
        ability.addTarget(new TargetPermanent(filter).setTargetTag(2).withChooseHint("to move a counter to"));
        this.addAbility(ability);
    }

    private RumorMonger(final RumorMonger card) {
        super(card);
    }

    @Override
    public RumorMonger copy() {
        return new RumorMonger(this);
    }
}
