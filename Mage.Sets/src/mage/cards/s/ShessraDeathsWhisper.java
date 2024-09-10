package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleTargetEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShessraDeathsWhisper extends CardImpl {

    public ShessraDeathsWhisper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Bewitching Whispers — When Shessra, Death's Whisper enters the battlefield, target creature blocks this turn if able.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BlocksIfAbleTargetEffect(Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.withFlavorWord("Bewitching Whispers"));

        // Whispers of the Grave — At the beginning of your end step, if a creature died this turn, you may pay 2 life. If you do, draw a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new DoIfCostPaid(new DrawCardSourceControllerEffect(1),
                new PayLifeCost(2)), TargetController.YOU, MorbidCondition.instance, false
        ).addHint(MorbidHint.instance).withFlavorWord("Whispers of the Grave"));
    }

    private ShessraDeathsWhisper(final ShessraDeathsWhisper card) {
        super(card);
    }

    @Override
    public ShessraDeathsWhisper copy() {
        return new ShessraDeathsWhisper(this);
    }
}
