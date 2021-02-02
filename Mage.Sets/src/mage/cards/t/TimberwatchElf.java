
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class TimberwatchElf extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Elves");

    static {
        filter.add(SubType.ELF.getPredicate());
    }

    public TimberwatchElf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);


        // {tap}: Target creature gets +X/+X until end of turn, where X is the number of Elves on the battlefield.
        PermanentsOnBattlefieldCount amount = new PermanentsOnBattlefieldCount(filter);
        Effect effect = new BoostTargetEffect(amount, amount, Duration.EndOfTurn, true);
        effect.setText("Target creature gets +X/+X until end of turn, where X is the number of Elves on the battlefield");
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                effect, new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private TimberwatchElf(final TimberwatchElf card) {
        super(card);
    }

    @Override
    public TimberwatchElf copy() {
        return new TimberwatchElf(this);
    }
}
