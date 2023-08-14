package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RonaTolarianObliterator extends CardImpl {

    public RonaTolarianObliterator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.nightCard = true;

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a source deals damage to Rona, Tolarian Obliterator, that source's controller exiles a card from their hand at random. If it's a land card, you may put it onto the battlefield under your control. Otherwise, you may cast it without paying its mana cost.
        this.addAbility(new RonaTolarianObliteratorTriggeredAbility());
    }

    private RonaTolarianObliterator(final RonaTolarianObliterator card) {
        super(card);
    }

    @Override
    public RonaTolarianObliterator copy() {
        return new RonaTolarianObliterator(this);
    }
}

class RonaTolarianObliteratorTriggeredAbility extends TriggeredAbilityImpl {

    RonaTolarianObliteratorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RonaTolarianObliteratorEffect());
    }

    private RonaTolarianObliteratorTriggeredAbility(final RonaTolarianObliteratorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RonaTolarianObliteratorTriggeredAbility copy() {
        return new RonaTolarianObliteratorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(getSourceId())) {
            this.getEffects().setTargetPointer(new FixedTarget(game.getControllerId(event.getSourceId())));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a source deals damage to {this}, that source's controller exiles a card " +
                "from their hand at random. If it's a land card, you may put it onto the battlefield " +
                "under your control. Otherwise, you may cast it without paying its mana cost.";
    }
}

class RonaTolarianObliteratorEffect extends OneShotEffect {

    RonaTolarianObliteratorEffect() {
        super(Outcome.Benefit);
    }

    private RonaTolarianObliteratorEffect(final RonaTolarianObliteratorEffect effect) {
        super(effect);
    }

    @Override
    public RonaTolarianObliteratorEffect copy() {
        return new RonaTolarianObliteratorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || player == null) {
            return false;
        }
        Card card = player.getHand().getRandom(game);
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        if (!card.isLand(game)) {
            return CardUtil.castSpellWithAttributesForFree(controller, source, game, card);
        }
        return controller.chooseUse(Outcome.PutLandInPlay, "Put " + card.getIdName() + " onto the battlefield?", source, game)
                && controller.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
