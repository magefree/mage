package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class IllusionistsBracers extends CardImpl {

    public IllusionistsBracers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever an ability of equipped creature is activated, if it isn't a mana ability, copy that ability. You may choose new targets for the copy.
        this.addAbility(new IllusionistsBracersTriggeredAbility());

        // Equip 3
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3)));
    }

    private IllusionistsBracers(final IllusionistsBracers card) {
        super(card);
    }

    @Override
    public IllusionistsBracers copy() {
        return new IllusionistsBracers(this);
    }
}

class IllusionistsBracersTriggeredAbility extends TriggeredAbilityImpl {

    IllusionistsBracersTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CopyActivatedAbilityEffect());
    }

    IllusionistsBracersTriggeredAbility(final IllusionistsBracersTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public IllusionistsBracersTriggeredAbility copy() {
        return new IllusionistsBracersTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent equipment = game.getPermanent(this.getSourceId());
        if (equipment != null && equipment.isAttachedTo(event.getSourceId())) {
            StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
            if (!(stackAbility.getStackAbility() instanceof ActivatedManaAbilityImpl)) {
                Effect effect = this.getEffects().get(0);
                effect.setValue("stackAbility", stackAbility);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an ability of equipped creature is activated, if it isn't a mana ability, copy that ability. You may choose new targets for the copy.";
    }
}

class CopyActivatedAbilityEffect extends OneShotEffect {

    public CopyActivatedAbilityEffect() {
        super(Outcome.Benefit);
        this.staticText = "copy that ability. You may choose new targets for the copy";
    }

    public CopyActivatedAbilityEffect(final CopyActivatedAbilityEffect effect) {
        super(effect);
    }

    @Override
    public CopyActivatedAbilityEffect copy() {
        return new CopyActivatedAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackAbility ability = (StackAbility) getValue("stackAbility");
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (ability != null && controller != null && sourcePermanent != null) {
            ability.createCopyOnStack(game, source, source.getControllerId(), true);
            game.informPlayers(sourcePermanent.getName() + ": " + controller.getLogName() + " copied activated ability");
            return true;
        }
        return false;
    }
}
