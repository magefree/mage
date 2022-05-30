package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AloraMerryThief extends CardImpl {

    public AloraMerryThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever you attack, up to one target attacking creature can't be blocked this turn. Return that creature to its owner's hand at the beginning of the next end step.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new CantBeBlockedTargetEffect(Duration.EndOfTurn), 1
        );
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ReturnToHandTargetEffect()
                        .setText("return that creature to its owner's hand")), true
        ).setText("Return that creature to its owner's hand at the beginning of the next end step"));
        ability.addTarget(new TargetAttackingCreature(0, 1));
        this.addAbility(ability);

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private AloraMerryThief(final AloraMerryThief card) {
        super(card);
    }

    @Override
    public AloraMerryThief copy() {
        return new AloraMerryThief(this);
    }
}
