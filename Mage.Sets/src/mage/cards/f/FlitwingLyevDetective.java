package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.CreateTokenEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.ClueArtifactToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class FlitwingLyevDetective extends CardImpl {

    public FlitwingLyevDetective(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If you would create one or more tokens, you may create that many Clue tokens instead.
        this.addAbility(new SimpleStaticAbility(new FlitwingLyevDetectiveEffect()));
    }

    private FlitwingLyevDetective(final FlitwingLyevDetective card) {
        super(card);
    }

    @Override
    public FlitwingLyevDetective copy() {
        return new FlitwingLyevDetective(this);
    }
}

class FlitwingLyevDetectiveEffect extends ReplacementEffectImpl {

    FlitwingLyevDetectiveEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, false);
        staticText = "if you would create one or more tokens, you may instead create that many Clue tokens instead";
    }

    private FlitwingLyevDetectiveEffect(final FlitwingLyevDetectiveEffect effect) {
        super(effect);
    }

    @Override
    public FlitwingLyevDetectiveEffect copy() {
        return new FlitwingLyevDetectiveEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATE_TOKEN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        CreateTokenEvent tokenEvent = (CreateTokenEvent) event;
        Player player = game.getPlayer(source.getControllerId());
        int amount = tokenEvent.getTokens().values().stream().mapToInt(x -> x).sum();
        if (player == null || amount < 1 || !player.chooseUse(
                outcome, "Replace this create token event with Clues?", source, game
        )) {
            return false;
        }
        Token token = new ClueArtifactToken();
        tokenEvent.getTokens().clear();
        tokenEvent.getTokens().put(token, amount);
        return false;
    }
}
