package mage.cards.p;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author Styxo
 */
public final class PiasRevolution extends CardImpl {

    public PiasRevolution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Whenever a nontoken artifact is put into your graveyard from the battlefield, return that card to your hand unless target opponent has Pia's Revolution deal 3 damage to them.
        Ability ability = new PiasRevolutionTriggeredAbility();
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private PiasRevolution(final PiasRevolution card) {
        super(card);
    }

    @Override
    public PiasRevolution copy() {
        return new PiasRevolution(this);
    }
}

class PiasRevolutionReturnEffect extends OneShotEffect {

    PiasRevolutionReturnEffect() {
        super(Outcome.Benefit);
        this.staticText = "return that card to your hand unless target opponent has {this} deal 3 damage to them";
    }

    private PiasRevolutionReturnEffect(final PiasRevolutionReturnEffect effect) {
        super(effect);
    }

    @Override
    public PiasRevolutionReturnEffect copy() {
        return new PiasRevolutionReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Set<Card> cards = new CardsImpl(getTargetPointer().getTargets(game, source)).getCards(game);
        if (controller == null || cards.isEmpty()) {
            return false;
        }
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (opponent != null && opponent.chooseUse(outcome,
                "Have Pia's Revolution deal 3 damage to you to prevent return to hand effect?",
                source, game)) {
            opponent.damage(3, source.getSourceId(), source, game);
        } else {
            controller.moveCards(cards, Zone.HAND, source, game);
        }
        return true;
    }
}

class PiasRevolutionTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent();

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(TargetController.YOU.getOwnerPredicate());
    }

    PiasRevolutionTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PiasRevolutionReturnEffect(), false);
        setTriggerPhrase("Whenever a nontoken artifact is put into your graveyard from the battlefield, ");
        setLeavesTheBattlefieldTrigger(true);
    }

    private PiasRevolutionTriggeredAbility(final PiasRevolutionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PiasRevolutionTriggeredAbility copy() {
        return new PiasRevolutionTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()) {
            Permanent permanent = zEvent.getTarget();
            if (permanent != null && filter.match(permanent, controllerId, this, game)) {
                getEffects().setTargetPointer(new FixedTargets(
                        CardUtil.getAllCardsFromPermanentLeftBattlefield(permanent, game), game
                ));
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject sourceObject, GameEvent event) {
        return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, sourceObject, event, game);
    }
}
