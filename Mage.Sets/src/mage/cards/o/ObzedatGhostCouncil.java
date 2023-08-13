package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class ObzedatGhostCouncil extends CardImpl {

    public ObzedatGhostCouncil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}{B}{B}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.ADVISOR);
        this.supertype.add(SuperType.LEGENDARY);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        //When Obzedat, Ghost Council enters the battlefield, target opponent loses 2 life and you gain 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeTargetEffect(2));
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        //At the beginning of your end step you may exile Obzedat. If you do, return it to the battlefield under its owner's 
        //control at the beginning of your next upkeep. It gains haste.
        Ability ability2 = new BeginningOfYourEndStepTriggeredAbility(new ObzedatGhostCouncilExileSourceEffect(), true);
        this.addAbility(ability2);
    }

    private ObzedatGhostCouncil(final ObzedatGhostCouncil card) {
        super(card);
    }

    @Override
    public ObzedatGhostCouncil copy() {
        return new ObzedatGhostCouncil(this);
    }
}

class ObzedatGhostCouncilExileSourceEffect extends OneShotEffect {

    ObzedatGhostCouncilExileSourceEffect() {
        super(Outcome.Exile);
        staticText = "exile {this}. If you do, return it to the battlefield under its owner's control "
                + "at the beginning of your next upkeep. It gains haste.";
    }

    private ObzedatGhostCouncilExileSourceEffect(final ObzedatGhostCouncilExileSourceEffect effect) {
        super(effect);
    }

    @Override
    public ObzedatGhostCouncilExileSourceEffect copy() {
        return new ObzedatGhostCouncilExileSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null
                || controller == null
                || !controller.moveCards(permanent, Zone.EXILED, source, game)) {
            return false;
        }
        game.addDelayedTriggeredAbility(new ObzedatGhostCouncilDelayedTriggeredAbility(permanent), source);
        return true;
    }

}

class ObzedatGhostCouncilDelayedTriggeredAbility extends DelayedTriggeredAbility {

    ObzedatGhostCouncilDelayedTriggeredAbility(Card card) {
        super(new ObzedatGhostCouncilReturnEffect(card));
    }

    private ObzedatGhostCouncilDelayedTriggeredAbility(ObzedatGhostCouncilDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId); // must be the controller who chooses
    }

    @Override
    public ObzedatGhostCouncilDelayedTriggeredAbility copy() {
        return new ObzedatGhostCouncilDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Return {this} to the battlefield under its owner's control at the beginning of your next upkeep. It gains haste";
    }
}

class ObzedatGhostCouncilReturnEffect extends OneShotEffect {

    private final Card card;

    ObzedatGhostCouncilReturnEffect(Card card) {
        super(Outcome.Benefit);
        this.card = card;
    }

    private ObzedatGhostCouncilReturnEffect(final ObzedatGhostCouncilReturnEffect effect) {
        super(effect);
        this.card = effect.card;
    }

    @Override
    public ObzedatGhostCouncilReturnEffect copy() {
        return new ObzedatGhostCouncilReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (card == null) {
            return false;
        }
        Player owner = game.getPlayer(card.getOwnerId());
        if (owner == null
                || !owner.moveCards(card, Zone.BATTLEFIELD, source, game)) { // comes back from any zone
            return false;
        }
        game.addEffect(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield), source);
        return true;
    }
}
