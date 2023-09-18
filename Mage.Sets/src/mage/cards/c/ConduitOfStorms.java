
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfMainPhaseDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class ConduitOfStorms extends CardImpl {

    public ConduitOfStorms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.WEREWOLF);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        this.secondSideCardClazz = mage.cards.c.ConduitOfEmrakul.class;

        // Whenever Conduit of Storms attacks, add {R} at the beginning of your next main phase this turn.
        Effect effect = new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfMainPhaseDelayedTriggeredAbility(
                        new AddManaToManaPoolSourceControllerEffect(Mana.RedMana(1)), false, TargetController.YOU, AtTheBeginOfMainPhaseDelayedTriggeredAbility.PhaseSelection.NEXT_MAIN_THIS_TURN));
        effect.setText("add {R} at the beginning of your next main phase this turn");
        this.addAbility(new AttacksTriggeredAbility(effect, false));
        // {3}{R}{R}: Transform Conduit of Storms.
        this.addAbility(new TransformAbility());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TransformSourceEffect(), new ManaCostsImpl<>("{3}{R}{R}")));
    }

    private ConduitOfStorms(final ConduitOfStorms card) {
        super(card);
    }

    @Override
    public ConduitOfStorms copy() {
        return new ConduitOfStorms(this);
    }
}
