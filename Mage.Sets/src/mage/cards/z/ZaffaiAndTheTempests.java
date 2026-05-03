package mage.cards.z;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.hint.common.ConditionPermanentHint;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.Watcher;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class ZaffaiAndTheTempests extends CardImpl {

    public ZaffaiAndTheTempests(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARD);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        // Once during each of your turns, you may cast an instant or sorcery spell from your hand without paying its mana cost.
        this.addAbility(
            new SimpleStaticAbility(new ZaffaiAndTheTempestsEffect())
                .setIdentifier(MageIdentifier.ZaffaiAndTheTempestsWatcher)
                .addHint(new ConditionPermanentHint(
                        ZaffaiAndTheTempestsCondition.instance,
                        "Can cast an instant or sorcery spell from your hand without paying its mana cost this turn"
                )),
            new ZaffaiAndTheTempestsWatcher()
        );
    }

    private ZaffaiAndTheTempests(final ZaffaiAndTheTempests card) {
        super(card);
    }

    @Override
    public ZaffaiAndTheTempests copy() {
        return new ZaffaiAndTheTempests(this);
    }
}

enum ZaffaiAndTheTempestsCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        ZaffaiAndTheTempestsWatcher watcher = game.getState().getWatcher(ZaffaiAndTheTempestsWatcher.class);
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        return watcher != null
            && sourceObject != null
            && game.isActivePlayer(source.getControllerId())
            && !watcher.isAbilityUsed(new MageObjectReference(sourceObject, game));
    }
}

class ZaffaiAndTheTempestsEffect extends AsThoughEffectImpl {

    ZaffaiAndTheTempestsEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.PlayForFree);
        staticText = "once during each of your turns, you may cast an instant or sorcery spell from your hand " +
                "without paying its mana cost.";
    }

    private ZaffaiAndTheTempestsEffect(final ZaffaiAndTheTempestsEffect effect) {
        super(effect);
    }

    @Override
    public ZaffaiAndTheTempestsEffect copy() {
        return new ZaffaiAndTheTempestsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!source.isControlledBy(affectedControllerId) || !game.isActivePlayer(source.getControllerId())) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        Card card = game.getCard(CardUtil.getMainCardId(game, objectId));
        ZaffaiAndTheTempestsWatcher watcher = game.getState().getWatcher(ZaffaiAndTheTempestsWatcher.class);
        if (controller == null
                || card == null
                || !card.isInstantOrSorcery(game)
                || watcher == null
                || sourceObject == null
                || watcher.isAbilityUsed(new MageObjectReference(sourceObject, game))) {
            return false;
        }
        if (!controller.getHand().contains(card.getId())) {
            return false;
        }

        allowCardToPlayWithoutMana(objectId, source, affectedControllerId, MageIdentifier.ZaffaiAndTheTempestsWatcher, game);
        return true;
    }
}

class ZaffaiAndTheTempestsWatcher extends Watcher {

    private final Set<MageObjectReference> usedFrom = new HashSet<>();

    public ZaffaiAndTheTempestsWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST
                && event.hasApprovingIdentifier(MageIdentifier.ZaffaiAndTheTempestsWatcher)) {
            usedFrom.add(event.getApprovingObject().getApprovingMageObjectReference());
        }
    }

    @Override
    public void reset() {
        super.reset();
        usedFrom.clear();
    }

    public boolean isAbilityUsed(MageObjectReference mor) {
        return usedFrom.contains(mor);
    }
}
