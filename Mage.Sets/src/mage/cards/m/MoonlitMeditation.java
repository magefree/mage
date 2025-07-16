package mage.cards.m;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.CreateTokenEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.TargetPermanent;
import mage.util.functions.CopyApplier;
import mage.util.functions.CopyTokenFunction;
import mage.util.functions.EmptyCopyApplier;
import mage.watchers.common.CreatedTokenWatcher;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoonlitMeditation extends CardImpl {

    public MoonlitMeditation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant artifact or creature you control
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // The first time you would create one or more tokens each turn, you may instead create that many tokens that are copies of enchanted permanent.
        this.addAbility(new SimpleStaticAbility(new MoonlitMeditationEffect()), new CreatedTokenWatcher());
    }

    private MoonlitMeditation(final MoonlitMeditation card) {
        super(card);
    }

    @Override
    public MoonlitMeditation copy() {
        return new MoonlitMeditation(this);
    }
}

class MoonlitMeditationEffect extends ReplacementEffectImpl {

    MoonlitMeditationEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, false);
        staticText = "the first time you would create one or more tokens each turn, " +
                "you may instead create that many tokens that are copies of enchanted permanent";
    }

    private MoonlitMeditationEffect(MoonlitMeditationEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATE_TOKEN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId())
                && !CreatedTokenWatcher.checkPlayer(source.getControllerId(), game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .orElse(null);
        if (permanent == null
                || !Optional
                .ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .filter(player -> player.chooseUse(
                        Outcome.Benefit, "Replace the tokens with copies of " +
                                permanent.getLogName() + '?', source, game
                ))
                .isPresent()) {
            return false;
        }
        CreateTokenEvent tokenEvent = (CreateTokenEvent) event;
        int amount = tokenEvent.getAmount();
        tokenEvent.getTokens().clear();
        tokenEvent.getTokens().put(copyPermanentToToken(permanent, game, source), amount);
        return false;
    }

    @Override
    public MoonlitMeditationEffect copy() {
        return new MoonlitMeditationEffect(this);
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
        Token token = CopyTokenFunction.createTokenCopy(copyFromPermanent, game); // needed so that entersBattlefied triggered abilities see the attributes (e.g. Master Biomancer)
        applier.apply(game, token, source, permanent.getId());
        return token;
    }
}
