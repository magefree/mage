package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArniMetalbrow extends CardImpl {

    public ArniMetalbrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a creature you control attacks or a creature enters the battlefield under your control attacking, you may pay {1}{R}. If you do, you may put a creature card with mana value less than that creature's mana value from your hand onto the battlefield tapped and attacking.
        this.addAbility(new ArniMetalbrowTriggeredAbility());
    }

    private ArniMetalbrow(final ArniMetalbrow card) {
        super(card);
    }

    @Override
    public ArniMetalbrow copy() {
        return new ArniMetalbrow(this);
    }
}

class ArniMetalbrowTriggeredAbility extends TriggeredAbilityImpl {

    ArniMetalbrowTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new ArniMetalbrowEffect(), new ManaCostsImpl<>("{1}{R}")));
        setTriggerPhrase("Whenever a creature you control attacks or a creature enters the battlefield under your control attacking, ");
    }

    private ArniMetalbrowTriggeredAbility(final ArniMetalbrowTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ArniMetalbrowTriggeredAbility copy() {
        return new ArniMetalbrowTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent;
        switch (event.getType()) {
            case ENTERS_THE_BATTLEFIELD:
                permanent = ((EntersTheBattlefieldEvent) event).getTarget();
                break;
            case ATTACKER_DECLARED:
                permanent = game.getPermanent(event.getSourceId());
                break;
            default:
                return false;
        }
        if (permanent == null
                || !permanent.isCreature(game)
                || !permanent.isAttacking()
                || !permanent.isControlledBy(getControllerId())) {
            return false;
        }
        this.getEffects().setValue("attacker", permanent);
        return true;
    }
}

class ArniMetalbrowEffect extends OneShotEffect {

    ArniMetalbrowEffect() {
        super(Outcome.Benefit);
        staticText = "you may put a creature card with mana value less than that creature's " +
                "mana value from your hand onto the battlefield tapped and attacking";
    }

    private ArniMetalbrowEffect(final ArniMetalbrowEffect effect) {
        super(effect);
    }

    @Override
    public ArniMetalbrowEffect copy() {
        return new ArniMetalbrowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = (Permanent) getValue("attacker");
        if (player == null || permanent == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getHand());
        cards.removeIf(uuid -> !game.getCard(uuid).isCreature(game));
        cards.removeIf(uuid -> game.getCard(uuid).getManaValue() >= permanent.getManaValue());
        if (cards.isEmpty()) {
            return false;
        }
        TargetCard target = new TargetCardInHand(0, 1, StaticFilters.FILTER_CARD_CREATURE);
        target.withChooseHint("to put onto the battlefield tapped and attacking");
        player.choose(Outcome.PutCreatureInPlay, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
        Permanent creature = game.getPermanent(card.getId());
        if (creature != null) {
            game.getCombat().addAttackingCreature(creature.getId(), game);
        }
        return true;
    }
}
