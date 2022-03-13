package mage.cards.e;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.CreateTokenEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EmptyToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;
import mage.util.functions.CopyApplier;
import mage.util.functions.EmptyCopyApplier;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EsixFractalBloom extends CardImpl {

    public EsixFractalBloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.FRACTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // The first time you would create one or more tokens during each of your turns, you may instead choose a creature other than Esix, Fractal Bloom and create that many tokens that are copies of that creature.
        this.addAbility(new SimpleStaticAbility(new EsixFractalBloomEffect()), new EsixFractalBloomWatcher());
    }

    private EsixFractalBloom(final EsixFractalBloom card) {
        super(card);
    }

    @Override
    public EsixFractalBloom copy() {
        return new EsixFractalBloom(this);
    }
}

class EsixFractalBloomEffect extends ReplacementEffectImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    EsixFractalBloomEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, false);
        staticText = "the first time you would create one or more tokens during each of your turns, " +
                "you may instead choose a creature other than {this} " +
                "and create that many tokens that are copies of that creature";
    }

    private EsixFractalBloomEffect(EsixFractalBloomEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATE_TOKEN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId())
                && game.isActivePlayer(source.getControllerId())
                && !EsixFractalBloomWatcher.checkPlayer(source.getControllerId(), game)
                && game.getBattlefield().count(
                filter, source.getControllerId(), source, game
        ) > 0;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, 1, filter, true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        if (event instanceof CreateTokenEvent) {
            CreateTokenEvent tokenEvent = (CreateTokenEvent) event;
            int amount = tokenEvent.getAmount();
            tokenEvent.getTokens().clear();
            tokenEvent.getTokens().put(copyPermanentToToken(permanent, game, source), amount);
        }
        return false;
    }

    @Override
    public EsixFractalBloomEffect copy() {
        return new EsixFractalBloomEffect(this);
    }

    private static Token copyPermanentToToken(Permanent permanent, Game game, Ability source) {
        CopyApplier applier = new EmptyCopyApplier();
        // handle copies of copies
        Permanent copyFromPermanent = permanent;
        for (ContinuousEffect effect : game.getState().getContinuousEffects().getLayeredEffects(game)) {
            if (!(effect instanceof CopyEffect)) {
                continue;
            }
            CopyEffect copyEffect = (CopyEffect) effect;
            // there is another copy effect that our targetPermanent copies stats from
            if (!copyEffect.getSourceId().equals(permanent.getId())) {
                continue;
            }
            MageObject object = ((CopyEffect) effect).getTarget();
            if (!(object instanceof Permanent)) {
                continue;
            }
            copyFromPermanent = (Permanent) object;
            if (copyEffect.getApplier() != null) {
                applier = copyEffect.getApplier();
            }
        }

        // create token and modify all attributes permanently (without game usage)
        EmptyToken token = new EmptyToken();
        CardUtil.copyTo(token).from(copyFromPermanent, game); // needed so that entersBattlefied triggered abilities see the attributes (e.g. Master Biomancer)
        applier.apply(game, token, source, permanent.getId());
        return token;
    }
}

class EsixFractalBloomWatcher extends Watcher {

    private final Set<UUID> createdThisTurn = new HashSet<>();

    EsixFractalBloomWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.CREATED_TOKEN) {
            createdThisTurn.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        createdThisTurn.clear();
    }

    static boolean checkPlayer(UUID playerId, Game game) {
        EsixFractalBloomWatcher watcher = game.getState().getWatcher(EsixFractalBloomWatcher.class);
        return watcher != null && watcher.createdThisTurn.contains(playerId);
    }
}
