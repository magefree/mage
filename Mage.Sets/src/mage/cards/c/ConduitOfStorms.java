package mage.cards.c;

import mage.Mana;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfMainPhaseDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ConduitOfStorms extends TransformingDoubleFacedCard {

    public ConduitOfStorms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF, SubType.HORROR}, "{2}{R}",
                "Conduit of Emrakul",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDRAZI, SubType.WEREWOLF}, "");

        // Conduit of Storms
        this.getLeftHalfCard().setPT(2, 3);

        // Whenever Conduit of Storms attacks, add {R} at the beginning of your next main phase this turn.
        this.getLeftHalfCard().addAbility(new AttacksTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfMainPhaseDelayedTriggeredAbility(
                        new AddManaToManaPoolSourceControllerEffect(Mana.RedMana(1)), false,
                        TargetController.YOU, AtTheBeginOfMainPhaseDelayedTriggeredAbility.PhaseSelection.NEXT_MAIN_THIS_TURN
                )
        ).setText("add {R} at the beginning of your next main phase this turn"), false));

        // {3}{R}{R}: Transform Conduit of Storms.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{3}{R}{R}")));

        // Conduit of Emrakul
        this.getRightHalfCard().setPT(5, 4);

        // Whenever Conduit of Emrakul attacks, add {C}{C} at the beginning of your next main phase this turn.
        this.getRightHalfCard().addAbility(new AttacksTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfMainPhaseDelayedTriggeredAbility(
                        new AddManaToManaPoolSourceControllerEffect(Mana.GenericMana(2)), false,
                        TargetController.YOU, AtTheBeginOfMainPhaseDelayedTriggeredAbility.PhaseSelection.NEXT_MAIN_THIS_TURN
                )
        ).setText("add {C}{C} at the beginning of your next main phase this turn"), false));
    }

    private ConduitOfStorms(final ConduitOfStorms card) {
        super(card);
    }

    @Override
    public ConduitOfStorms copy() {
        return new ConduitOfStorms(this);
    }
}
