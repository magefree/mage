package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.*;
import mage.constants.*;
import static mage.constants.Outcome.Benefit;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.targetpointer.FixedTarget;

/**
 * @author TheElk801
 */
public final class TectonicGiant extends CardImpl {

    public TectonicGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Tectonic Giant attacks or becomes the target of a spell an opponent controls, choose one —
        // • Tectonic Giant deals 3 damage to each opponent.
        // • Exile the top two cards of your library. Choose one of them. Until the end of your next turn, you may play that card.
        this.addAbility(new TectonicGiantTriggeredAbility());
    }

    private TectonicGiant(final TectonicGiant card) {
        super(card);
    }

    @Override
    public TectonicGiant copy() {
        return new TectonicGiant(this);
    }
}

class TectonicGiantTriggeredAbility extends TriggeredAbilityImpl {

    TectonicGiantTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamagePlayersEffect(3, TargetController.OPPONENT), false);
        this.addMode(new Mode(new TectonicGiantEffect()));
        setTriggerPhrase("Whenever {this} attacks or becomes the target of a spell an opponent controls, ");
    }

    private TectonicGiantTriggeredAbility(final TectonicGiantTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS
                || event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (event.getType()) {
            case DECLARED_ATTACKERS:
                return game.getCombat().getAttackers().contains(this.getSourceId());
            case TARGETED:
                if (event.getTargetId().equals(getSourceId())) {
                    MageObject mageObject = game.getObject(event.getSourceId());
                    Player player = game.getPlayer(getControllerId());
                    return mageObject != null
                            && mageObject instanceof Spell
                            && player != null
                            && player.hasOpponent(((Spell) mageObject).getControllerId(), game);
                }
        }
        return false;
    }

    @Override
    public TectonicGiantTriggeredAbility copy() {
        return new TectonicGiantTriggeredAbility(this);
    }
}

class TectonicGiantEffect extends OneShotEffect {

    TectonicGiantEffect() {
        super(Benefit);
        staticText = "exile the top two cards of your library. Choose one of them. "
                + "Until the end of your next turn, you may play that card";
    }

    private TectonicGiantEffect(final TectonicGiantEffect effect) {
        super(effect);
    }

    @Override
    public TectonicGiantEffect copy() {
        return new TectonicGiantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 2));
        controller.moveCards(cards, Zone.EXILED, source, game);
        TargetCard targetCard = new TargetCardInExile(StaticFilters.FILTER_CARD);
        controller.choose(outcome, cards, targetCard, game);

        Card card = game.getCard(targetCard.getFirstTarget());
        if (card == null) {
            return true;
        }
        ContinuousEffect effect = new TectonicGiantMayPlayEffect();
        effect.setTargetPointer(new FixedTarget(card.getId()));
        game.addEffect(effect, source);

        return true;
    }
}

class TectonicGiantMayPlayEffect extends AsThoughEffectImpl {

    private int castOnTurn = 0;

    TectonicGiantMayPlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.staticText = "Until the end of your next turn, you may play that card.";
    }

    private TectonicGiantMayPlayEffect(final TectonicGiantMayPlayEffect effect) {
        super(effect);
        castOnTurn = effect.castOnTurn;
    }

    @Override
    public TectonicGiantMayPlayEffect copy() {
        return new TectonicGiantMayPlayEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        castOnTurn = game.getTurnNum();
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        return castOnTurn != game.getTurnNum()
                && game.getPhase().getStep().getType() == PhaseStep.END_TURN
                && game.isActivePlayer(source.getControllerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId)
                && getTargetPointer().getTargets(game, source).contains(sourceId);
    }
}
