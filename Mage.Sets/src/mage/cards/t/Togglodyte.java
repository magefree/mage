package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalReplacementEffect;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author L_J
 */
public final class Togglodyte extends CardImpl {

    public Togglodyte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Togglodyte enters the battlefield turned on.
        this.addAbility(new EntersBattlefieldAbility(new TogglodyteEntersEffect()));

        // Whenever a player casts a spell, toggle Togglodyte’s ON/OFF switch.
        this.addAbility(new SpellCastAllTriggeredAbility(new TogglodyteToggleEffect(), false));

        // As long as Togglodyte is turned off, it can’t attack or block, and prevent all damage it would deal.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalRestrictionEffect(new TogglodyteRestrictionEffect(), new TogglodyteCondition()));
        ability.addEffect(new ConditionalReplacementEffect(new TogglodytePreventionEffect(), new TogglodyteCondition()));
        this.addAbility(ability);
    }

    private Togglodyte(final Togglodyte card) {
        super(card);
    }

    @Override
    public Togglodyte copy() {
        return new Togglodyte(this);
    }
}

class TogglodyteEntersEffect extends OneShotEffect {

    public TogglodyteEntersEffect() {
        super(Outcome.Neutral);
        staticText = "turned on";
    }

    public TogglodyteEntersEffect(final TogglodyteEntersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (mageObject == null) {
            mageObject = game.getObject(source);
        }
        if (mageObject != null) {
            boolean toggled = true;
            game.getState().setValue(mageObject.getId() + "_toggle", toggled);
            if (mageObject instanceof Permanent) {
                ((Permanent) mageObject).addInfo("toggle", CardUtil.addToolTipMarkTags("Switch: " + (toggled ? "ON" : "OFF")), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public TogglodyteEntersEffect copy() {
        return new TogglodyteEntersEffect(this);
    }
}

class TogglodyteToggleEffect extends OneShotEffect {

    public TogglodyteToggleEffect() {
        super(Outcome.Neutral);
        staticText = "toggle {this}'s ON/OFF switch";
    }

    public TogglodyteToggleEffect(final TogglodyteToggleEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            if (game.getState().getValue(mageObject.getId() + "_toggle") == null) {
                return false;
            }
            boolean toggled = Boolean.TRUE.equals(game.getState().getValue(mageObject.getId() + "_toggle"));
            game.getState().setValue(mageObject.getId() + "_toggle", !toggled);
            ((Permanent) mageObject).addInfo("toggle", CardUtil.addToolTipMarkTags("Switch: " + (!toggled ? "ON" : "OFF")), game);
            return true;
        }
        return false;
    }

    @Override
    public TogglodyteToggleEffect copy() {
        return new TogglodyteToggleEffect(this);
    }
}

class TogglodyteRestrictionEffect extends RestrictionEffect {

    public TogglodyteRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "";
    }

    public TogglodyteRestrictionEffect(final TogglodyteRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public TogglodyteRestrictionEffect copy() {
        return new TogglodyteRestrictionEffect(this);
    }
}

class TogglodytePreventionEffect extends PreventionEffectImpl {

    public TogglodytePreventionEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false);
        staticText = "As long as {this} is turned off, it can't attack or block, and prevent all damage it would deal";
    }

    public TogglodytePreventionEffect(final TogglodytePreventionEffect effect) {
        super(effect);
    }

    @Override
    public TogglodytePreventionEffect copy() {
        return new TogglodytePreventionEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            return event.getSourceId().equals(source.getSourceId());
        }
        return false;
    }
}

class TogglodyteCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return !Boolean.TRUE.equals(game.getState().getValue(mageObject.getId() + "_toggle"));
        }
        return false;
    }
}
