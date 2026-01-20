package mage.cards.m;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.CreateTokenEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.util.functions.CopyApplier;
import mage.util.functions.CopyTokenFunction;
import mage.util.functions.EmptyCopyApplier;
import mage.watchers.common.CreatedTokenWatcher;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MirrormindCrown extends CardImpl {

    public MirrormindCrown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.subtype.add(SubType.EQUIPMENT);

        // As long as this Equipment is attached to a creature, the first time you would create one or more tokens each turn, you may instead create that many tokens that are copies of equipped creature.
        this.addAbility(new SimpleStaticAbility(new MirrormindCrownEffect()), new CreatedTokenWatcher());

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private MirrormindCrown(final MirrormindCrown card) {
        super(card);
    }

    @Override
    public MirrormindCrown copy() {
        return new MirrormindCrown(this);
    }
}

class MirrormindCrownEffect extends ReplacementEffectImpl {

    MirrormindCrownEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, false);
        staticText = "as long as {this} is attached to a creature, " +
                "the first time you would create one or more tokens each turn, " +
                "you may instead create that many tokens that are copies of equipped creature";
    }

    private MirrormindCrownEffect(MirrormindCrownEffect effect) {
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
                && !CreatedTokenWatcher.checkPlayer(source.getControllerId(), game)
                && Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .filter(permanent -> permanent.isCreature(game))
                .isPresent();
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (!(event instanceof CreateTokenEvent)) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .orElse(null);
        if (player == null || permanent == null
                || !player.chooseUse(outcome, "Have the tokens be copies of the equipped creature?", source, game)) {
            return false;
        }
        CreateTokenEvent tokenEvent = (CreateTokenEvent) event;
        int amount = tokenEvent.getAmount();
        tokenEvent.getTokens().clear();
        tokenEvent.getTokens().put(copyPermanentToToken(permanent, game, source), amount);
        return false;
    }

    @Override
    public MirrormindCrownEffect copy() {
        return new MirrormindCrownEffect(this);
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
