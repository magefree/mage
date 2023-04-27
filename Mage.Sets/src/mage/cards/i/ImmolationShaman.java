package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackAbility;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImmolationShaman extends CardImpl {

    public ImmolationShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever an opponent activates an ability of an artifact, creature, or land that isn't a mana ability, Immolation Shaman deals 1 damage to that player.
        this.addAbility(new ImmolationShamanTriggeredAbility());

        // {3}{R}{R}: Immolation Shaman gets +3/+3 and gains menace until end of turn.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(
                        3, 3, Duration.EndOfTurn
                ).setText("{this} gets +3/+3"),
                new ManaCostsImpl<>("{3}{R}{R}")
        );
        ability.addEffect(new GainAbilitySourceEffect(
                new MenaceAbility(), Duration.EndOfTurn
        ).setText("and gains menace until end of turn"));
        this.addAbility(ability);
    }

    private ImmolationShaman(final ImmolationShaman card) {
        super(card);
    }

    @Override
    public ImmolationShaman copy() {
        return new ImmolationShaman(this);
    }
}

class ImmolationShamanTriggeredAbility extends TriggeredAbilityImpl {

    ImmolationShamanTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(StaticValue.get(1), true, "that player", true));
    }

    private ImmolationShamanTriggeredAbility(final ImmolationShamanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ImmolationShamanTriggeredAbility copy() {
        return new ImmolationShamanTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(getControllerId())) {
            Card source = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (source != null && (source.isArtifact(game) || source.isCreature(game) || source.isLand(game))) {
                StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
                if (!(stackAbility.getStackAbility() instanceof ActivatedManaAbilityImpl)) {
                    for (Effect effect : getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent activates an ability of an artifact, creature, or land on the battlefield, " +
                "if it isn't a mana ability, {this} deals 1 damage to that player.";
    }
}
