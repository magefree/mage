package mage.cards.d;

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
import mage.game.permanent.token.MutagenToken;
import mage.game.permanent.token.Token;
import mage.util.CardUtil;

import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DonatelloTheBrains extends CardImpl {

    public DonatelloTheBrains(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // If one or more tokens would be created under your control, those tokens plus a Mutagen token are created instead.
        this.addAbility(new SimpleStaticAbility(new DonatelloTheBrainsReplacementEffect()));

        // Partner--Character select
        this.addAbility(PartnerVariantType.CHARACTER_SELECT.makeAbility());
    }

    private DonatelloTheBrains(final DonatelloTheBrains card) {
        super(card);
    }

    @Override
    public DonatelloTheBrains copy() {
        return new DonatelloTheBrains(this);
    }
}

class DonatelloTheBrainsReplacementEffect extends ReplacementEffectImpl {

    DonatelloTheBrainsReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "if one or more tokens would be created under your control, " +
                "those tokens plus a Mutagen token are created instead";
    }

    private DonatelloTheBrainsReplacementEffect(final DonatelloTheBrainsReplacementEffect effect) {
        super(effect);
    }

    @Override
    public DonatelloTheBrainsReplacementEffect copy() {
        return new DonatelloTheBrainsReplacementEffect(this);
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
        Map<Token, Integer> tokens = ((CreateTokenEvent) event).getTokens();
        Token token = CardUtil
                .castStream(tokens.values(), MutagenToken.class)
                .findAny()
                .orElseGet(MutagenToken::new);
        tokens.compute(token, CardUtil::setOrIncrementValue);
        return false;
    }
}
