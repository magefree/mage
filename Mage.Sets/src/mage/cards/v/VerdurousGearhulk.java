package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author fireshoes
 */
public final class VerdurousGearhulk extends CardImpl {


    public VerdurousGearhulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Verdurous Gearhulk enters the battlefield, distribute four +1/+1 counters among any number of target creatures you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DistributeCountersEffect(CounterType.P1P1, 4, false, "any number of target creatures you control"), false);
        ability.addTarget(new TargetCreaturePermanentAmount(4, StaticFilters.FILTER_CONTROLLED_CREATURES));
        this.addAbility(ability);
    }

    private VerdurousGearhulk(final VerdurousGearhulk card) {
        super(card);
    }

    @Override
    public VerdurousGearhulk copy() {
        return new VerdurousGearhulk(this);
    }
}
