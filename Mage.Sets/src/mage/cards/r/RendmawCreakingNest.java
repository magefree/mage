package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.Black22BirdToken;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class RendmawCreakingNest extends CardImpl {

    public RendmawCreakingNest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{B}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Rendmaw, Creaking Nest enters and whenever you play a card with two or more card types, each player creates a tapped 2/2 black Bird creature token with flying. The tokens are goaded for the rest of the game.
        this.addAbility(new RendmawCreakingNestTriggeredAbility());
    }

    private RendmawCreakingNest(final RendmawCreakingNest card) {
        super(card);
    }

    @Override
    public RendmawCreakingNest copy() {
        return new RendmawCreakingNest(this);
    }
}

class RendmawCreakingNestTriggeredAbility extends TriggeredAbilityImpl {

    RendmawCreakingNestTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RendmawCreakingNestEffect());
        setTriggerPhrase("When {this} enters and whenever you play a card with two or more card types, ");
    }

    private RendmawCreakingNestTriggeredAbility(final RendmawCreakingNestTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD
                || event.getType() == GameEvent.EventType.SPELL_CAST
                || event.getType() == GameEvent.EventType.LAND_PLAYED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (event.getType()) {
            case ENTERS_THE_BATTLEFIELD:
                return event.getTargetId().equals(getSourceId());
            case SPELL_CAST:
                if (!event.getPlayerId().equals(this.getControllerId())) {
                    return false;
                }
                Spell spell = game.getSpellOrLKIStack(event.getTargetId());
                return spell != null && spell.getCardType(game).size() >= 2;
            case LAND_PLAYED:
                if (!event.getPlayerId().equals(this.getControllerId())) {
                    return false;
                }
                Card card = game.getCard(event.getTargetId());
                return card != null && card.getCardType(game).size() >= 2;
            default:
                return false;
        }
    }

    @Override
    public RendmawCreakingNestTriggeredAbility copy() {
        return new RendmawCreakingNestTriggeredAbility(this);
    }
}

class RendmawCreakingNestEffect extends OneShotEffect {

    RendmawCreakingNestEffect() {
        super(Outcome.Benefit);
        staticText = "each player creates a tapped 2/2 black Bird creature token with flying. " +
                "The tokens are goaded for the rest of the game";
    }

    private RendmawCreakingNestEffect(final RendmawCreakingNestEffect effect) {
        super(effect);
    }

    @Override
    public RendmawCreakingNestEffect copy() {
        return new RendmawCreakingNestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game, true)) {
            Token token = new Black22BirdToken();
            token.putOntoBattlefield(1, game, source, playerId, true, false);
            token.getLastAddedTokenIds().forEach(id -> game.addEffect(
                    new GoadTargetEffect().setDuration(Duration.EndOfGame).setTargetPointer(new FixedTarget(id, game)), source
            ));
        }
        return true;
    }
}
