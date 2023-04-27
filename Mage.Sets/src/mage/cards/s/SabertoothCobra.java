
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UnlessPaysDelayedEffect;
import mage.abilities.effects.common.counter.AddPoisonCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.PhaseStep;

/**
 *
 * @author LoneFox
 */
public final class SabertoothCobra extends CardImpl {

    public SabertoothCobra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Sabertooth Cobra deals damage to a player, they get a poison counter. That player gets another poison counter at the beginning of their next upkeep unless they pay {2} before that turn.
        Effect effect = new AddPoisonCounterTargetEffect(1);
        effect.setText("that player gets a poison counter");
        Ability ability = new DealsDamageToAPlayerTriggeredAbility(effect, false, true);
        effect = new AddPoisonCounterTargetEffect(1);
        effect.setText("That player gets another poison counter.");
        ability.addEffect(new UnlessPaysDelayedEffect(new ManaCostsImpl<>("{2}"), effect, PhaseStep.UPKEEP, true,
            "That player gets another poison counter at the beginning of their next upkeep unless they pay {2} before that turn."));
        this.addAbility(ability);
    }

    private SabertoothCobra(final SabertoothCobra card) {
        super(card);
    }

    @Override
    public SabertoothCobra copy() {
        return new SabertoothCobra(this);
    }
}
