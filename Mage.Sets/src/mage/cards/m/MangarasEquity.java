package mage.cards.m;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class MangarasEquity extends CardImpl {

    public MangarasEquity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");

        // As Mangara's Equity enters the battlefield, choose black or red.
        this.addAbility(new AsEntersBattlefieldAbility(new MangarasEquityColorChoiceEffect()));

        // At the beginning of your upkeep, sacrifice Mangara's Equity unless you pay {1}{W}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl<>("{1}{W}")), TargetController.YOU, false));

        // Whenever a creature of the chosen color deals damage to you or a white creature you control, Mangara's Equity deals that much damage to that creature.
        this.addAbility(new MangarasEquityTriggeredAbility());
    }

    private MangarasEquity(final MangarasEquity card) {
        super(card);
    }

    @Override
    public MangarasEquity copy() {
        return new MangarasEquity(this);
    }
}

class MangarasEquityColorChoiceEffect extends OneShotEffect {

    public MangarasEquityColorChoiceEffect() {
        super(Outcome.Neutral);
        staticText = "choose black or red";
    }

    private MangarasEquityColorChoiceEffect(final MangarasEquityColorChoiceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent == null) {
            return false;
        }

        ChoiceColor choice = new ChoiceColor(true, "Choose a color.", permanent);
        choice.removeColorFromChoices("White");
        choice.removeColorFromChoices("Blue");
        choice.removeColorFromChoices("Green");

        if (controller == null || !controller.choose(outcome, choice, game)) {
            return false;
        }

        game.informPlayers(permanent.getLogName() + ": " + controller.getLogName() + " has chosen " + choice.getChoice());
        game.getState().setValue(permanent.getId() + "_color", choice.getColor());
        permanent.addInfo("chosen color", CardUtil.addToolTipMarkTags("Chosen color: " + choice.getChoice()), game);

        return true;
    }

    @Override
    public MangarasEquityColorChoiceEffect copy() {
        return new MangarasEquityColorChoiceEffect(this);
    }
}

class MangarasEquityTriggeredAbility extends TriggeredAbilityImpl {

    MangarasEquityTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(SavedDamageValue.MUCH));
    }

    private MangarasEquityTriggeredAbility(final MangarasEquityTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent mangarasEquity = game.getPermanentOrLKIBattlefield(getSourceId());
        if (mangarasEquity == null) {
            return false;
        }

        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER) {
            if (!event.getTargetId().equals(getControllerId())) {
                return false;
            }
        } else {
            Permanent damagedPermanent = game.getPermanent(event.getTargetId());
            if (damagedPermanent == null || !damagedPermanent.isControlledBy(getControllerId())
                    || !damagedPermanent.isCreature() || !damagedPermanent.getColor().shares(ObjectColor.WHITE)) {
                return false;
            }
        }

        Permanent damageSource = game.getPermanent(event.getSourceId());
        ObjectColor chosenColor = (ObjectColor) game.getState().getValue(mangarasEquity.getId() + "_color");
        if (damageSource != null && chosenColor != null
                && damageSource.isCreature() && damageSource.getColor().shares(chosenColor)) {
            for (Effect effect : getEffects()) {
                if (effect instanceof DamageTargetEffect) {
                    effect.setTargetPointer(new FixedTarget(damageSource.getId(), game));
                }
            }
            this.getEffects().setValue("damage", event.getAmount());

            return true;
        }

        return false;
    }

    @Override
    public MangarasEquityTriggeredAbility copy() {
        return new MangarasEquityTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature of the chosen color deals damage to you or a white creature you control, "
                + "{this} deals that much damage to that creature.";
    }
}
