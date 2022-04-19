package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.CreateTokenEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.CatHasteToken;
import mage.game.permanent.token.DogVigilanceToken;
import mage.game.permanent.token.Token;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JinnieFayJetmirsSecond extends CardImpl {

    public JinnieFayJetmirsSecond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R/G}{G}{G/W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // If you would create one or more tokens, you may instead create that many 2/2 green Cat creature tokens with haste or that many 3/1 green Dog creature tokens with vigilance.
        this.addAbility(new SimpleStaticAbility(new JinnieFayJetmirsSecondEffect()));
    }

    private JinnieFayJetmirsSecond(final JinnieFayJetmirsSecond card) {
        super(card);
    }

    @Override
    public JinnieFayJetmirsSecond copy() {
        return new JinnieFayJetmirsSecond(this);
    }
}

class JinnieFayJetmirsSecondEffect extends ReplacementEffectImpl {

    JinnieFayJetmirsSecondEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, false);
        staticText = "if you would create one or more tokens, you may instead create that many 2/2 green " +
                "Cat creature tokens with haste or that many 3/1 green Dog creature tokens with vigilance";
    }

    private JinnieFayJetmirsSecondEffect(final JinnieFayJetmirsSecondEffect effect) {
        super(effect);
    }

    @Override
    public JinnieFayJetmirsSecondEffect copy() {
        return new JinnieFayJetmirsSecondEffect(this);
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
                outcome, "Replace this token event?", source, game
        )) {
            return false;
        }
        Token token = player.chooseUse(
                outcome, "Choose 2/2 Cat or 3/1 Dog", null,
                "Cat", "Dog", source, game
        ) ? new CatHasteToken() : new DogVigilanceToken();
        tokenEvent.getTokens().clear();
        tokenEvent.getTokens().put(token, amount);
        return false;
    }
}
