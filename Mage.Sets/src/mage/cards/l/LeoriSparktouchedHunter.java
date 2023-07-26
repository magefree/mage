package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.hint.StaticHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoicePlaneswalkerType;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class LeoriSparktouchedHunter extends CardImpl {

    public LeoriSparktouchedHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Leori, Sparktouched Hunter deals combat damage to a player, choose a planeswalker type. Until end of turn, whenever you activate an ability of a planeswalker of that type, copy that ability. You may choose new targets for the copies.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
            new LeoriSparktouchedHunterEffect(),
            false
        ));
    }

    private LeoriSparktouchedHunter(final LeoriSparktouchedHunter card) {
        super(card);
    }

    @Override
    public LeoriSparktouchedHunter copy() {
        return new LeoriSparktouchedHunter(this);
    }
}


class LeoriSparktouchedHunterEffect extends OneShotEffect {

    LeoriSparktouchedHunterEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose a planeswalker type. Until end of turn, whenever you activate an ability " +
            "of a planeswalker of that type, copy that ability. You may choose new targets for the copies.";
    }

    private LeoriSparktouchedHunterEffect(final LeoriSparktouchedHunterEffect effect) {
        super(effect);
    }

    @Override
    public LeoriSparktouchedHunterEffect copy() {
        return new LeoriSparktouchedHunterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Choice choice = new ChoicePlaneswalkerType(game.getObject(source));
        if (!controller.choose(outcome, choice, game)) {
            return false;
        }

        SubType subType = SubType.fromString(choice.getChoice());
        if (subType == null) {
            return false;
        }

        game.informPlayers(controller.getLogName() + " has chosen " + subType);

        game.addDelayedTriggeredAbility(
            new LeoriSparktouchedHunterTriggeredAbility(subType),
            source
        );

        return true;
    }
}


class LeoriSparktouchedHunterTriggeredAbility extends DelayedTriggeredAbility {

    private final SubType subType;

    LeoriSparktouchedHunterTriggeredAbility(SubType subType) {
        super(new CopyStackObjectEffect(), Duration.EndOfTurn, false);
        this.subType = subType;
        this.addHint(new StaticHint("Chosen Subtype: " + subType));
    }

    private LeoriSparktouchedHunterTriggeredAbility(final LeoriSparktouchedHunterTriggeredAbility ability) {
        super(ability);
        this.subType = ability.subType;
    }

    @Override
    public LeoriSparktouchedHunterTriggeredAbility copy() {
        return new LeoriSparktouchedHunterTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }

        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
        if (stackAbility == null
            || stackAbility.getStackAbility() instanceof ActivatedManaAbilityImpl) {
            return false;
        }

        Permanent permanent = game.getPermanentOrLKIBattlefield(stackAbility.getSourceId());
        if (permanent == null || !permanent.isPlaneswalker(game) || !permanent.hasSubtype(subType, game)) {
            return false;
        }
        this.getEffects().setValue("stackObject", stackAbility);
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you activate an ability of a planeswalker of the chosen type, copy that ability. " +
            "You may choose new targets for the copies.";
    }
}
