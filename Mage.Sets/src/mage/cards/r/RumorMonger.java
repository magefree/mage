
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.MoveCountersTargetsEffect;
import mage.abilities.keyword.BountyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class RumorMonger extends CardImpl {

    public RumorMonger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{R}{G}");
        this.subtype.add(SubType.ARCONA);
        this.subtype.add(SubType.HUNTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Rumor Monger enters the battlefield, put a bounty counter on up to two target creatures an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()));
        ability.addTarget(new TargetOpponentsCreaturePermanent(0, 2));
        this.addAbility(ability);

        // <i>Bounty</i> &mdash; Whenever a creature an opponent controls with a bounty counter on it dies, you may move a bounty counter from one target creature to another target creatute.
        ability = new BountyAbility(new MoveCountersTargetsEffect(CounterType.BOUNTY, 1), false);
        ability.addTarget(new TargetOpponentsCreaturePermanent(2));
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
