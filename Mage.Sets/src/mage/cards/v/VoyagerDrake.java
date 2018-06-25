
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class VoyagerDrake extends CardImpl {

    private final UUID originalId;

    public VoyagerDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Multikicker {U}
        this.addAbility(new MultikickerAbility("{U}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Voyager Drake enters the battlefield, up to X target creatures gain flying until end of turn, where X is the number of times Voyager Drake was kicked.
        //TODO this should trigger even if it wasn't kicked at all
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), false),
                KickedCondition.instance,
                "When {this} enters the battlefield, up to X target creatures gain flying until end of turn, where X is the number of times {this} was kicked.");
        originalId = ability.getOriginalId();
        this.addAbility(ability);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            ability.getTargets().clear();
            int numbTargets = new MultikickerCount().calculate(game, ability, null);
            if (numbTargets > 0) {
                ability.addTarget(new TargetCreaturePermanent(0, numbTargets));
            }
        }
    }

    public VoyagerDrake(final VoyagerDrake card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public VoyagerDrake copy() {
        return new VoyagerDrake(this);
    }
}
