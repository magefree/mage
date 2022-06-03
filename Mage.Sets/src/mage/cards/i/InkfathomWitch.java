
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.SetPowerToughnessAllEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.UnblockedPredicate;

/**
 *
 * @author LevelX2
 */
public final class InkfathomWitch extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Each unblocked creature");
    static {
        filter.add(UnblockedPredicate.instance);
    }

    public InkfathomWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U/B}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Fear
        this.addAbility(FearAbility.getInstance());
        // {2}{U}{B}: Each unblocked creature has base power and toughness 4/1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SetPowerToughnessAllEffect(4, 1, Duration.EndOfTurn, filter, true), new ManaCostsImpl<>("{2}{U}{B}")));
    }

    private InkfathomWitch(final InkfathomWitch card) {
        super(card);
    }

    @Override
    public InkfathomWitch copy() {
        return new InkfathomWitch(this);
    }
}
