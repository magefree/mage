package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.CreateTokenEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.FishNoAbilityToken;
import mage.game.permanent.token.OctopusToken;
import mage.game.permanent.token.Shark33Token;
import mage.game.permanent.token.Token;
import mage.players.Player;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * Note to future reader: this card's replacement effects do NOT affect tokens with Changeling such as those of Birthing Boughs. This is not a bug, see 701.6b
 * @author PurpleCrowbar
 */
public final class FishersTalent extends CardImpl {

    public FishersTalent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{U}");
        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // At the beginning of your upkeep, look at the top card of your library. You may reveal it if it's a land card. Create a 1/1 blue Fish creature token if you revealed it this way. Then draw a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new FishersTalentLevel1Effect()));

        // {G}{U}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{G}{U}"));

        // If you would create a Fish token, create a 3/3 blue Shark creature token instead.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(new SimpleStaticAbility(new FishersTalentLevel2Effect()), 2)));

        // {2}{G}{U}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{2}{G}{U}"));

        // If you would create a Shark token, create an 8/8 blue Octopus creature token instead.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(new SimpleStaticAbility(new FishersTalentLevel3Effect()), 3)));
    }

    private FishersTalent(final FishersTalent card) {
        super(card);
    }

    @Override
    public FishersTalent copy() {
        return new FishersTalent(this);
    }
}

class FishersTalentLevel1Effect extends OneShotEffect {

    FishersTalentLevel1Effect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top card of your library. You may reveal it if it's a land card. " +
                "Create a 1/1 blue Fish creature token if you revealed it this way. Then draw a card.";
    }

    private FishersTalentLevel1Effect(final FishersTalentLevel1Effect effect) {
        super(effect);
    }

    @Override
    public FishersTalentLevel1Effect copy() {
        return new FishersTalentLevel1Effect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card topCard = controller.getLibrary().getFromTop(game);
        if (topCard != null) {
            controller.lookAtCards("Top card of library", topCard, game);
            if (topCard.isLand(game)) {
                if (controller.chooseUse(Outcome.PutCreatureInPlay, "Reveal " + topCard.getLogName() + " to create a 1/1 blue Fish creature token?", source, game)) {
                    controller.revealCards(source, new CardsImpl(topCard), game);
                    new FishNoAbilityToken().putOntoBattlefield(1, game, source);
                }
            }
        }
        controller.drawCards(1, source, game);
        return true;
    }
}

class FishersTalentLevel2Effect extends ReplacementEffectImpl {

    FishersTalentLevel2Effect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "If you would create a Fish token, create a 3/3 blue Shark creature token instead";
    }

    private FishersTalentLevel2Effect(final FishersTalentLevel2Effect effect) {
        super(effect);
    }

    @Override
    public FishersTalentLevel2Effect copy() {
        return new FishersTalentLevel2Effect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATE_TOKEN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!(event instanceof CreateTokenEvent) || !event.getPlayerId().equals(source.getControllerId())) {
            return false;
        }
        for (Token token : ((CreateTokenEvent) event).getTokens().keySet()) {
            if (token.hasSubtype(SubType.FISH, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int amount = 0;
        Map<Token, Integer> tokens = ((CreateTokenEvent) event).getTokens();
        for (Iterator<Map.Entry<Token, Integer>> iter = tokens.entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry<Token, Integer> entry = iter.next();
            Token token = entry.getKey();
            if (token.hasSubtype(SubType.FISH, game)) {
                amount += entry.getValue();
                iter.remove();
            }
        }

        tokens.put(new Shark33Token(), amount);
        return false;
    }
}

class FishersTalentLevel3Effect extends ReplacementEffectImpl {

    FishersTalentLevel3Effect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "If you would create a Shark token, create an 8/8 blue Octopus creature token instead";
    }

    private FishersTalentLevel3Effect(final FishersTalentLevel3Effect effect) {
        super(effect);
    }

    @Override
    public FishersTalentLevel3Effect copy() {
        return new FishersTalentLevel3Effect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATE_TOKEN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!(event instanceof CreateTokenEvent) || !event.getPlayerId().equals(source.getControllerId())) {
            return false;
        }
        for (Token token : ((CreateTokenEvent) event).getTokens().keySet()) {
            if (token.hasSubtype(SubType.SHARK, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int amount = 0;
        Map<Token, Integer> tokens = ((CreateTokenEvent) event).getTokens();
        for (Iterator<Map.Entry<Token, Integer>> iter = tokens.entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry<Token, Integer> entry = iter.next();
            Token token = entry.getKey();
            if (token.hasSubtype(SubType.SHARK, game)) {
                amount += entry.getValue();
                iter.remove();
            }
        }

        tokens.put(new OctopusToken(), amount);
        return false;
    }
}
