package mage.cards.c;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.mana.ManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Plopman
 */
public final class CarpetOfFlowers extends CardImpl {

    public CarpetOfFlowers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // At the beginning of each of your main phases, if you haven't added mana 
        // with this ability this turn, you may add up to X mana of any one color, 
        // where X is the number of Islands target opponent controls.
        this.addAbility(new CarpetOfFlowersTriggeredAbility());
    }

    private CarpetOfFlowers(final CarpetOfFlowers card) {
        super(card);
    }

    @Override
    public CarpetOfFlowers copy() {
        return new CarpetOfFlowers(this);
    }
}

class CarpetOfFlowersTriggeredAbility extends TriggeredAbilityImpl {

    CarpetOfFlowersTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CarpetOfFlowersEffect(), true);
        this.addTarget(new TargetOpponent());
    }

    CarpetOfFlowersTriggeredAbility(final CarpetOfFlowersTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CarpetOfFlowersTriggeredAbility copy() {
        return new CarpetOfFlowersTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PRECOMBAT_MAIN_PHASE_PRE
                || event.getType() == GameEvent.EventType.POSTCOMBAT_MAIN_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return !Boolean.TRUE.equals(game.getState().getValue(this.originalId.toString()
                + "addMana"
                + game.getState().getZoneChangeCounter(sourceId)));
    }

    @Override
    public boolean resolve(Game game) {
        boolean value = super.resolve(game);
        if (value == true) {
            game.getState().setValue(this.originalId.toString()
                            + "addMana"
                            + game.getState().getZoneChangeCounter(sourceId),
                    Boolean.TRUE);
        }
        return value;
    }

    @Override
    public void reset(Game game) {
        game.getState().setValue(this.originalId.toString()
                        + "addMana"
                        + game.getState().getZoneChangeCounter(sourceId),
                Boolean.FALSE);
    }

    @Override
    public String getTriggerPhrase() {
        return "At the beginning of each of your main phases, if "
                + "you haven't added mana with this ability this turn, "
                ;
    }

}

class CarpetOfFlowersEffect extends ManaEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Island ");

    static {
        filter.add(SubType.ISLAND.getPredicate());
        filter.add(CardType.LAND.getPredicate());
    }

    CarpetOfFlowersEffect() {
        super();
        staticText = "you may add X mana of any one color, where X is the number of Islands target opponent controls";
    }

    CarpetOfFlowersEffect(final CarpetOfFlowersEffect effect) {
        super(effect);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        if (game != null) {
            int count = game.getBattlefield().count(filter, source.getTargets().getFirstTarget(), source, game);
            if (count > 0) {
                netMana.add(Mana.AnyMana(count));
            }
        }
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
        Player controller = game.getPlayer(source.getControllerId());
        ChoiceColor choice = new ChoiceColor();
        if (controller != null && controller.choose(Outcome.Benefit, choice, game)) {
            int count = game.getBattlefield().count(filter, source.getTargets().getFirstTarget(), source, game);
            if (count > 0) {
                switch (choice.getChoice()) {
                    case "Black":
                        mana.setBlack(count);
                        break;
                    case "Blue":
                        mana.setBlue(count);
                        break;
                    case "Red":
                        mana.setRed(count);
                        break;
                    case "Green":
                        mana.setGreen(count);
                        break;
                    case "White":
                        mana.setWhite(count);
                        break;
                    default:
                        break;
                }
            }
        }
        return mana;
    }

    @Override
    public CarpetOfFlowersEffect copy() {
        return new CarpetOfFlowersEffect(this);
    }
}
