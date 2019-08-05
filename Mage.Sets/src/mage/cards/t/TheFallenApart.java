package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author L_J
 */
public final class TheFallenApart extends CardImpl {

    public TheFallenApart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // The Fallen Apart enters the battlefield with two arms and two legs.
        this.addAbility(new EntersBattlefieldAbility(new TheFallenApartEntersEffect()));

        // Whenever damage is dealt to The Fallen Apart, remove an arm or a leg from it.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new TheFallenApartToggleEffect(), false));

        // The Fallen Apart can’t attack if it has no legs and can’t block if it has no arms.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TheFallenApartRestrictionEffect()));
    }

    public TheFallenApart(final TheFallenApart card) {
        super(card);
    }

    @Override
    public TheFallenApart copy() {
        return new TheFallenApart(this);
    }
}

class TheFallenApartEntersEffect extends OneShotEffect {

    public TheFallenApartEntersEffect() {
        super(Outcome.Neutral);
        staticText = "with two arms and two legs";
    }

    public TheFallenApartEntersEffect(final TheFallenApartEntersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (mageObject == null) {
            mageObject = game.getObject(source.getSourceId());
        }
        if (mageObject != null) {
            game.getState().setValue(mageObject.getId() + "_arms", 2);
            game.getState().setValue(mageObject.getId() + "_legs", 2);
            if (mageObject instanceof Permanent) {
                ((Permanent) mageObject).addInfo("armslegs", CardUtil.addToolTipMarkTags("Arms: 2, Legs: 2"), game);
            }
            return true;
        }
        return false;
    }

    @Override
    public TheFallenApartEntersEffect copy() {
        return new TheFallenApartEntersEffect(this);
    }
}

class TheFallenApartToggleEffect extends OneShotEffect {

    public TheFallenApartToggleEffect() {
        super(Outcome.Neutral);
        staticText = "remove an arm or a leg from it";
    }

    public TheFallenApartToggleEffect(final TheFallenApartToggleEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source.getSourceId());
        if (controller != null && mageObject != null) {
            if (game.getState().getValue(mageObject.getId() + "_arms") == null
                    || game.getState().getValue(mageObject.getId() + "_legs") == null) {
                return false;
            }
            int arms = (Integer) game.getState().getValue(mageObject.getId() + "_arms");
            int legs = (Integer) game.getState().getValue(mageObject.getId() + "_legs");
            if (arms > 0) {
                if (legs > 0) {
                    if (controller.chooseUse(Outcome.Detriment, "Remove an arm or a leg:",
                            source.getSourceObject(game).getLogName(), "Arm", "Leg", source, game)) {
                        arms -= 1;
                        game.informPlayers(mageObject.getLogName() + " loses an arm");
                    } else {
                        legs -= 1;
                        game.informPlayers(mageObject.getLogName() + " loses a leg");
                    }
                } else {
                    arms -= 1;
                    game.informPlayers(mageObject.getLogName() + " loses an arm");
                }
            } else {
                if (legs > 0) {
                    legs -= 1;
                    game.informPlayers(mageObject.getLogName() + " loses a leg");
                }
            }
            game.getState().setValue(mageObject.getId() + "_arms", arms);
            game.getState().setValue(mageObject.getId() + "_legs", legs);
            ((Permanent) mageObject).addInfo("armslegs", CardUtil.addToolTipMarkTags("Arms: " + arms + ", Legs: " + legs), game);
            return true;
        }
        return false;
    }

    @Override
    public TheFallenApartToggleEffect copy() {
        return new TheFallenApartToggleEffect(this);
    }
}

class TheFallenApartRestrictionEffect extends RestrictionEffect {

    public TheFallenApartRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can’t attack if it has no legs and can’t block if it has no arms";
    }

    public TheFallenApartRestrictionEffect(final TheFallenApartRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return (Integer) game.getState().getValue(mageObject.getId() + "_arms") > 0;
        }
        return false;
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return (Integer) game.getState().getValue(mageObject.getId() + "_legs") > 0;
        }
        return false;
    }

    @Override
    public TheFallenApartRestrictionEffect copy() {
        return new TheFallenApartRestrictionEffect(this);
    }
}
