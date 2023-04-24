package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OwlbearCub extends CardImpl {

    public OwlbearCub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.BEAR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Mama's Coming — Whenever Owlbear Cub attacks a player who controls eight or more lands, look at the top eight cards of your library. You may put a creature card from among them onto the battlefield tapped and attacking that player. Put the rest on the bottom of your library in random order.
        this.addAbility(new OwlbearCubTriggeredAbility());
    }

    private OwlbearCub(final OwlbearCub card) {
        super(card);
    }

    @Override
    public OwlbearCub copy() {
        return new OwlbearCub(this);
    }
}

class OwlbearCubTriggeredAbility extends TriggeredAbilityImpl {

    OwlbearCubTriggeredAbility() {
        super(Zone.BATTLEFIELD, new OwlbearCubEffect());
        this.withFlavorWord("Mama's Coming");
        setTriggerPhrase("Whenever Owlbear Cub attacks a player who controls eight or more lands, ");
    }

    private OwlbearCubTriggeredAbility(final OwlbearCubTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OwlbearCubTriggeredAbility copy() {
        return new OwlbearCubTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getCombat().getAttackers().contains(this.getSourceId())) {
            return false;
        }
        Player player = game.getPlayer(game.getCombat().getDefenderId(this.getSourceId()));
        if (player == null || game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                player.getId(), this, game
        ) < 8) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(player.getId()));
        return true;
    }
}

class OwlbearCubEffect extends OneShotEffect {

    OwlbearCubEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top eight cards of your library. You may put a creature card " +
                "from among them onto the battlefield tapped and attacking that player. " +
                "Put the rest on the bottom of your library in random order";
    }

    private OwlbearCubEffect(final OwlbearCubEffect effect) {
        super(effect);
    }

    @Override
    public OwlbearCubEffect copy() {
        return new OwlbearCubEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 8));
        TargetCard target = new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD_CREATURE);
        player.choose(outcome, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
            Permanent permanent = game.getPermanent(card.getId());
            if (permanent != null) {
                game.getCombat().addAttackerToCombat(permanent.getId(), getTargetPointer().getFirst(game, source), game);
            }
        }
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
