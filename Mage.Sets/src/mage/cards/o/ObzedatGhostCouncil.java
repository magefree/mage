package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
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

/**
 *
 * @author Plopman
 */
public final class ObzedatGhostCouncil extends CardImpl {

    public ObzedatGhostCouncil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}{B}{B}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.ADVISOR);
        addSuperType(SuperType.LEGENDARY);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        //When Obzedat, Ghost Council enters the battlefield, target opponent loses 2 life and you gain 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeTargetEffect(2));
        ability.addEffect(new GainLifeEffect(2).setText("and you gain 2 life"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
        //At the beginning of your end step you may exile Obzedat. If you do, return it to the battlefield under its owner's control at the beginning of your next upkeep. It gains haste.
        Ability ability2 = new BeginningOfYourEndStepTriggeredAbility(new ObzedatGhostCouncilExileSourceEffect(), true);
        ability2.addEffect(new CreateDelayedTriggeredAbilityEffect(new BeginningOfYourUpkeepdelayTriggeredAbility()));
        this.addAbility(ability2);
    }

    public ObzedatGhostCouncil(final ObzedatGhostCouncil card) {
        super(card);
    }

    @Override
    public ObzedatGhostCouncil copy() {
        return new ObzedatGhostCouncil(this);
    }
}

class ObzedatGhostCouncilExileSourceEffect extends OneShotEffect {

    public ObzedatGhostCouncilExileSourceEffect() {
        super(Outcome.Exile);
        staticText = "exile {this}";
    }

    public ObzedatGhostCouncilExileSourceEffect(final ObzedatGhostCouncilExileSourceEffect effect) {
        super(effect);
    }

    @Override
    public ObzedatGhostCouncilExileSourceEffect copy() {
        return new ObzedatGhostCouncilExileSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            return permanent.moveToExile(source.getSourceId(), permanent.getName(), source.getSourceId(), game);
        }
        return false;
    }

}

class BeginningOfYourUpkeepdelayTriggeredAbility extends DelayedTriggeredAbility {

    public BeginningOfYourUpkeepdelayTriggeredAbility() {
        this(new ObzedatGhostCouncilReturnEffect(), TargetController.YOU);
        this.addEffect(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.Custom));
    }

    public BeginningOfYourUpkeepdelayTriggeredAbility(Effect effect, TargetController targetController) {
        super(effect);
    }

    public BeginningOfYourUpkeepdelayTriggeredAbility(BeginningOfYourUpkeepdelayTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.controllerId);
    }

    @Override
    public BeginningOfYourUpkeepdelayTriggeredAbility copy() {
        return new BeginningOfYourUpkeepdelayTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "If you do, return it to the battlefield under its owner's control at the beginning of your next upkeep. It gains haste";
    }
}

class ObzedatGhostCouncilReturnEffect extends OneShotEffect {

    public ObzedatGhostCouncilReturnEffect() {
        super(Outcome.Benefit);
    }

    public ObzedatGhostCouncilReturnEffect(final ObzedatGhostCouncilReturnEffect effect) {
        super(effect);
    }

    @Override
    public ObzedatGhostCouncilReturnEffect copy() {
        return new ObzedatGhostCouncilReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            Zone zone = game.getState().getZone(source.getSourceId());
            // return it from every public zone - http://www.mtgsalvation.com/forums/magic-fundamentals/magic-rulings/magic-rulings-archives/513186-obzedat-gc-as-edh-commander
            if (zone != Zone.BATTLEFIELD && zone != Zone.LIBRARY && zone != Zone.HAND) {
                Player owner = game.getPlayer(card.getOwnerId());
                if (owner != null) {
                    owner.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
            return true;
        }
        return false;
    }

}
