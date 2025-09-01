package mage.cards.c;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfMainPhaseDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ConduitOfEmrakul extends CardImpl {

    public ConduitOfEmrakul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Whenever Conduit of Emrakul attacks, add {C}{C} at the beginning of your next main phase this turn.
        this.addAbility(new AttacksTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfMainPhaseDelayedTriggeredAbility(
                        new AddManaToManaPoolSourceControllerEffect(Mana.GenericMana(2)), false,
                        TargetController.YOU, AtTheBeginOfMainPhaseDelayedTriggeredAbility.PhaseSelection.NEXT_MAIN_THIS_TURN
                )
        ).setText("add {C}{C} at the beginning of your next main phase this turn"), false));
    }

    private ConduitOfEmrakul(final ConduitOfEmrakul card) {
        super(card);
    }

    @Override
    public ConduitOfEmrakul copy() {
        return new ConduitOfEmrakul(this);
    }
}
