
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class BattlefieldMedic extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(SubType.CLERIC.getPredicate());
    }

    public BattlefieldMedic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Prevent the next X damage that would be dealt to target creature this turn, where X is the number of Clerics on the battlefield.
        Ability ability = new SimpleActivatedAbility(
                new PreventDamageToTargetEffect(
                        Duration.EndOfTurn,
                        false,
                        true,
                        new PermanentsOnBattlefieldCount(filter)
                ).setText(
                        "prevent the next X damage "
                        + "that would be dealt to target creature this turn, "
                        + "where X is the number of Clerics on the battlefield"
                ),
                new TapSourceCost()
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BattlefieldMedic(final BattlefieldMedic card) {
        super(card);
    }

    @Override
    public BattlefieldMedic copy() {
        return new BattlefieldMedic(this);
    }
}
