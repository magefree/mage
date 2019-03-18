
package mage.cards.c;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Plopman
 */
public final class CarpetOfFlowers extends CardImpl {

    public CarpetOfFlowers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // At the beginning of each of your main phases, if you haven't added mana with this ability this turn, you may add up to X mana of any one color, where X is the number of Islands target opponent controls.
        this.addAbility(new CarpetOfFlowersTriggeredAbility());
    }

    public CarpetOfFlowers(final CarpetOfFlowers card) {
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
        return event.getType() == EventType.PRECOMBAT_MAIN_PHASE_PRE
                || event.getType() == EventType.POSTCOMBAT_MAIN_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Boolean activatedThisTurn = (Boolean) game.getState().getValue(this.originalId.toString() + "addMana");
        if (activatedThisTurn == null) {
            return true;
        } else {
            return !activatedThisTurn;
        }
    }

    @Override
    public boolean resolve(Game game) {
        boolean value = super.resolve(game);
        if (value == true) {
            game.getState().setValue(this.originalId.toString() + "addMana", Boolean.TRUE);
        }
        return value;
    }

    @Override
    public void reset(Game game) {
        game.getState().setValue(this.originalId.toString() + "addMana", Boolean.FALSE);
    }

    @Override
    public String getRule() {
        return "At the beginning of each of your main phases, if you haven't added mana with this ability this turn, " + super.getRule();
    }

}

class CarpetOfFlowersEffect extends ManaEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Island ");

    static {
        filter.add(new SubtypePredicate(SubType.ISLAND));
        filter.add(new CardTypePredicate(CardType.LAND));
    }

    CarpetOfFlowersEffect() {
        super();
        staticText = "add X mana of any one color, where X is the number of Islands target opponent controls";
    }

    CarpetOfFlowersEffect(final CarpetOfFlowersEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            checkToFirePossibleEvents(getMana(game, source), game, source);
            controller.getManaPool().addMana(getMana(game, source), game, source);
            return true;
        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ChoiceColor choice = new ChoiceColor();
        if (controller != null && controller.choose(Outcome.Benefit, choice, game)) {
            Mana mana = new Mana();
            int count = game.getBattlefield().count(filter, source.getSourceId(), source.getTargets().getFirstTarget(), game);
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
            return mana;
        }
        return null;
    }

    @Override
    public CarpetOfFlowersEffect copy() {
        return new CarpetOfFlowersEffect(this);
    }
}
