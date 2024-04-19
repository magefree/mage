package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.emblems.TamiyoSeasonedScholarEmblem;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TamiyoSeasonedScholar extends CardImpl {

    public TamiyoSeasonedScholar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TAMIYO);
        this.setStartingLoyalty(2);

        this.color.setGreen(true);
        this.color.setBlue(true);
        this.nightCard = true;

        // +2: Until your next turn, whenever a creature attacks you or a planeswalker you control, it gets -1/-0 until end of turn.
        this.addAbility(new LoyaltyAbility(new TamiyoSeasonedScholarPlus2Effect(), 2));

        // -3: Return target instant or sorcery card from your graveyard to your hand. If it's a green card, add one mana of any color.
        Ability ability = new LoyaltyAbility(new TamiyoSeasonedScholarMinus3Effect(), -3);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // -7: Draw cards equal to half the number of cards in your library, rounded up. You get an emblem with "You have no maximum hand size."
        ability = new LoyaltyAbility(new DrawCardSourceControllerEffect(TamiyoSeasonedScholarValue.instance)
                .setText("Draw cards equal to half the number of cards in your library, rounded up."), -7);
        ability.addEffect(new GetEmblemEffect(new TamiyoSeasonedScholarEmblem()));
        this.addAbility(ability);
    }

    private TamiyoSeasonedScholar(final TamiyoSeasonedScholar card) {
        super(card);
    }

    @Override
    public TamiyoSeasonedScholar copy() {
        return new TamiyoSeasonedScholar(this);
    }
}

class TamiyoSeasonedScholarPlus2Effect extends OneShotEffect {

    TamiyoSeasonedScholarPlus2Effect() {
        super(Outcome.PlayForFree);
        this.staticText = "Until your next turn, whenever a creature attacks you or a planeswalker you control, "
                + "it gets -1/-0 until end of turn";
    }

    private TamiyoSeasonedScholarPlus2Effect(final TamiyoSeasonedScholarPlus2Effect effect) {
        super(effect);
    }

    @Override
    public TamiyoSeasonedScholarPlus2Effect copy() {
        return new TamiyoSeasonedScholarPlus2Effect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addDelayedTriggeredAbility(new TamiyoSeasonedScholarDelayedTriggeredAbility(game.getTurnNum()), source);
        return true;
    }
}

class TamiyoSeasonedScholarDelayedTriggeredAbility extends DelayedTriggeredAbility {

    // TODO: Duration.UntilYourNextTurn seems to have issues according to other cards
    //       comments like [[Dont Move]], [[Jace, Architect of Thought]].
    //       This is the workaround other cards have implemented.
    private final int startingTurn;

    public TamiyoSeasonedScholarDelayedTriggeredAbility(int startingTurn) {
        super(new BoostTargetEffect(-1, 0, Duration.EndOfTurn), Duration.Custom, false);
        this.startingTurn = startingTurn;
    }

    private TamiyoSeasonedScholarDelayedTriggeredAbility(final TamiyoSeasonedScholarDelayedTriggeredAbility ability) {
        super(ability);
        this.startingTurn = ability.startingTurn;
    }

    @Override
    public TamiyoSeasonedScholarDelayedTriggeredAbility copy() {
        return new TamiyoSeasonedScholarDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent == null || !permanent.isCreature(game)) {
            return false;
        }
        // Is it attacking you?
        Player attackedPlayer = game.getPlayer(event.getTargetId());
        if (attackedPlayer != null && attackedPlayer.getId().equals(getControllerId())) {
            this.getEffects().setTargetPointer(new FixedTarget(permanent, game));
            return true;
        }
        // Is it attacking a planeswalker you control?
        Permanent attackedPermanent = game.getPermanent(event.getTargetId());
        if (attackedPermanent != null && attackedPermanent.getControllerId().equals(getControllerId())) {
            this.getEffects().setTargetPointer(new FixedTarget(permanent, game));
            return true;
        }
        return false;
    }

    @Override
    public boolean isInactive(Game game) {
        return game.getActivePlayerId().equals(getControllerId()) && game.getTurnNum() != startingTurn;
    }
}

class TamiyoSeasonedScholarMinus3Effect extends OneShotEffect {

    TamiyoSeasonedScholarMinus3Effect() {
        super(Outcome.DrawCard);
        this.staticText = "Return target instant or sorcery card from your graveyard to your hand. "
                + "If it's a green card, add one mana of any color";
    }

    private TamiyoSeasonedScholarMinus3Effect(final TamiyoSeasonedScholarMinus3Effect effect) {
        super(effect);
    }

    @Override
    public TamiyoSeasonedScholarMinus3Effect copy() {
        return new TamiyoSeasonedScholarMinus3Effect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        if (card == null) {
            return false;
        }
        Effect effect = new ReturnToHandTargetEffect();
        effect.setTargetPointer(getTargetPointer().copy());
        effect.apply(game, source);
        if (card.getColor(game).isGreen()) {
            new AddManaOfAnyColorEffect().apply(game, source);
        }
        return true;
    }
}

enum TamiyoSeasonedScholarValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        return player != null ? (player.getLibrary().size() + 1) / 2 : 0;
    }

    @Override
    public TamiyoSeasonedScholarValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "";
    }
}
