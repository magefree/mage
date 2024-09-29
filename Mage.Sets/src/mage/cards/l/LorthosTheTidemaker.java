package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class LorthosTheTidemaker extends CardImpl {

    public LorthosTheTidemaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OCTOPUS);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Whenever Lorthos, the Tidemaker attacks, you may pay {8}. If you do, tap up to eight target permanents. Those permanents don't untap during their controllers' next untap steps.
        DoIfCostPaid effect = new DoIfCostPaid(new TapTargetEffect(), new GenericManaCost(8), "Pay {8} to tap up to eight target permanents? (They don't untap during their controllers' next untap steps)");
        AttacksTriggeredAbility ability = new AttacksTriggeredAbility(effect, false);
        Effect effect2 = new DontUntapInControllersNextUntapStepTargetEffect();
        effect2.setText("Those permanents don't untap during their controllers' next untap steps");
        effect.addEffect(effect2);
        ability.addTarget(new TargetPermanent(0, 8, StaticFilters.FILTER_PERMANENTS, false));
        this.addAbility(ability);
    }

    private LorthosTheTidemaker(final LorthosTheTidemaker card) {
        super(card);
    }

    @Override
    public LorthosTheTidemaker copy() {
        return new LorthosTheTidemaker(this);
    }
}
