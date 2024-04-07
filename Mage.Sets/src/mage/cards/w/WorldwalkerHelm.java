package mage.cards.w;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.CreateTokenEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.MapToken;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WorldwalkerHelm extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent("artifact token you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public WorldwalkerHelm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        // If you would create one or more artifact tokens, instead create those tokens plus an additional Map token.
        this.addAbility(new SimpleStaticAbility(new WorldwalkerHelmReplacementEffect()));

        // {1}{U}, {T}: Create a token that's a copy of target artifact token you control.
        Ability ability = new SimpleActivatedAbility(new CreateTokenCopyTargetEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private WorldwalkerHelm(final WorldwalkerHelm card) {
        super(card);
    }

    @Override
    public WorldwalkerHelm copy() {
        return new WorldwalkerHelm(this);
    }
}

class WorldwalkerHelmReplacementEffect extends ReplacementEffectImpl {

    WorldwalkerHelmReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "if you would create one or more artifact tokens, instead create those tokens plus an additional Map token";
    }

    private WorldwalkerHelmReplacementEffect(final WorldwalkerHelmReplacementEffect effect) {
        super(effect);
    }

    @Override
    public WorldwalkerHelmReplacementEffect copy() {
        return new WorldwalkerHelmReplacementEffect(this);
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
        if (!(event instanceof CreateTokenEvent)) {
            return false;
        }
        CreateTokenEvent tokenEvent = (CreateTokenEvent) event;
        if (tokenEvent
                .getTokens()
                .keySet()
                .stream()
                .noneMatch(MageObject::isArtifact)) {
            return false;
        }
        MapToken mapToken = CardUtil
                .castStream(tokenEvent.getTokens().keySet().stream(), MapToken.class)
                .findFirst()
                .orElseGet(MapToken::new);
        tokenEvent
                .getTokens()
                .compute(mapToken, CardUtil::setOrIncrementValue);
        return false;
    }
}
