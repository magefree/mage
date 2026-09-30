package mage.cards.d;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.CreateTokenEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.Dragon55Token;
import mage.game.permanent.token.Token;
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
public final class DraconicVisitor extends CardImpl {

    public DraconicVisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If one or more artifact tokens would be created under your control, that many 5/5 red Dragon creature tokens with flying are created instead.
        this.addAbility(new SimpleStaticAbility(new DraconicVisitorEffect()));
    }

    private DraconicVisitor(final DraconicVisitor card) {
        super(card);
    }

    @Override
    public DraconicVisitor copy() {
        return new DraconicVisitor(this);
    }
}

class DraconicVisitorEffect extends ReplacementEffectImpl {

    DraconicVisitorEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "If one or more artifact tokens would be created under your control, that many 5/5 red Dragon creature tokens with flying are created instead";
    }

    private DraconicVisitorEffect(final DraconicVisitorEffect effect) {
        super(effect);
    }

    @Override
    public DraconicVisitorEffect copy() {
        return new DraconicVisitorEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATE_TOKEN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!(event instanceof CreateTokenEvent) || !source.isControlledBy(event.getPlayerId())) {
            return false;
        }
        return ((CreateTokenEvent) event).getTokens().keySet().stream().anyMatch(MageObject::isArtifact);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Dragon55Token dragonToken = null;
        int amount = 0;
        Map<Token, Integer> tokens = ((CreateTokenEvent) event).getTokens();
        for (Iterator<Map.Entry<Token, Integer>> iter = tokens.entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry<Token, Integer> entry = iter.next();
            Token token = entry.getKey();
            if (token instanceof Dragon55Token) {
                dragonToken = (Dragon55Token) token;
            }
            if (token.isArtifact()) {
                amount += entry.getValue();
                iter.remove();
            }
        }
        if (dragonToken == null) {
            dragonToken = new Dragon55Token();
        }
        tokens.put(dragonToken, tokens.getOrDefault(dragonToken, 0) + amount);
        return false;
    }
}
