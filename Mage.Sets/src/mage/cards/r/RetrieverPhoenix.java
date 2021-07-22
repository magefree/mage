package mage.cards.r;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.LearnEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RetrieverPhoenix extends CardImpl {

    public RetrieverPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Retriever Phoenix enters the battlefield, if you cast it, learn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new LearnEffect()), CastFromEverywhereSourceCondition.instance,
                "When {this} enters the battlefield, if you cast it, " + LearnEffect.getDefaultText()
        ).addHint(OpenSideboardHint.instance));

        // As long as Retriever Phoenix is in your graveyard, if you would learn, you may instead return Retriever Phoenix to the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.GRAVEYARD, new RetrieverPhoenixEffect()));
    }

    private RetrieverPhoenix(final RetrieverPhoenix card) {
        super(card);
    }

    @Override
    public RetrieverPhoenix copy() {
        return new RetrieverPhoenix(this);
    }
}

class RetrieverPhoenixEffect extends ReplacementEffectImpl {

    RetrieverPhoenixEffect() {
        super(Duration.WhileInGraveyard, Outcome.PutCreatureInPlay);
        staticText = "as long as {this} is in your graveyard, if you would learn, " +
                "you may instead return {this} to the battlefield";
    }

    private RetrieverPhoenixEffect(final RetrieverPhoenixEffect effect) {
        super(effect);
    }

    @Override
    public RetrieverPhoenixEffect copy() {
        return new RetrieverPhoenixEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        Player player = game.getPlayer(source.getControllerId());
        return sourceObject instanceof Card
                && player != null
                && player.chooseUse(outcome, "Return " + sourceObject.getName() + " to the battlefield instead of learning?", source, game)
                && player.moveCards((Card) sourceObject, Zone.BATTLEFIELD, source, game);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LEARN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }
}
