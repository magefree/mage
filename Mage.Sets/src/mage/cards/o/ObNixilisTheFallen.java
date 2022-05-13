
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class ObNixilisTheFallen extends CardImpl {

    public ObNixilisTheFallen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Landfall - Whenever a land enters the battlefield under your control, you may have target player lose 3 life.
        // If you do, put three +1/+1 counters on Ob Nixilis, the Fallen.
        Ability ability = new LandfallAbility(new LoseLifeTargetEffect(3).setText("target player lose 3 life"), true);
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)).concatBy("If you do,"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private ObNixilisTheFallen(final ObNixilisTheFallen card) {
        super(card);
    }

    @Override
    public ObNixilisTheFallen copy() {
        return new ObNixilisTheFallen(this);
    }
}
