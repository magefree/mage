package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.constants.*;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 *
 * @author NinthWorld
 */
public final class SiegeTank extends CardImpl {

    public SiegeTank(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{R}{W}");
        
        this.subtype.add(SubType.TERRAN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Siege Tank doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepSourceEffect()));

        // Morph {R}{W}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{R}{W}")));

        // At the beginning of your end step, you may turn Siege Tank face down.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new BecomesFaceDownCreatureEffect(Duration.Custom, BecomesFaceDownCreatureEffect.FaceDownType.MANUAL),
                TargetController.YOU, true));
    }

    public SiegeTank(final SiegeTank card) {
        super(card);
    }

    @Override
    public SiegeTank copy() {
        return new SiegeTank(this);
    }
}
