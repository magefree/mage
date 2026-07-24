package mage.cards.v;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObjectReference;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.Watcher;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.hint.common.ConditionPermanentHint;
import mage.abilities.keyword.FlyingAbility;
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
public final class VisionSpectralSynthezoid extends CardImpl {

    public VisionSpectralSynthezoid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Once during each of your turns, you may cast a noncreature or Robot spell from your hand without paying its mana cost.
        this.addAbility(
            new SimpleStaticAbility(new VisionSpectralSynthezoidEffect())
                .setIdentifier(MageIdentifier.VisionSpectralSynthezoidWatcher)
                .addHint(new ConditionPermanentHint(
                        VisionSpectralSynthezoidCondition.instance,
                        "Can cast a noncreature or Robot spell from your hand without paying its mana cost this turn"
                )),
            new VisionSpectralSynthezoidWatcher()
        );
    }

    private VisionSpectralSynthezoid(final VisionSpectralSynthezoid card) {
        super(card);
    }

    @Override
    public VisionSpectralSynthezoid copy() {
        return new VisionSpectralSynthezoid(this);
    }
}

enum VisionSpectralSynthezoidCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        VisionSpectralSynthezoidWatcher watcher = game.getState().getWatcher(VisionSpectralSynthezoidWatcher.class);
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        return watcher != null
            && sourceObject != null
            && game.isActivePlayer(source.getControllerId())
            && !watcher.isAbilityUsed(new MageObjectReference(sourceObject, game));
    }
}

class VisionSpectralSynthezoidEffect extends AsThoughEffectImpl {

    VisionSpectralSynthezoidEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.PlayForFree);
        staticText = "once during each of your turns, you may cast a noncreature or Robot spell from your hand " +
                "without paying its mana cost.";
    }

    private VisionSpectralSynthezoidEffect(final VisionSpectralSynthezoidEffect effect) {
        super(effect);
    }

    @Override
    public VisionSpectralSynthezoidEffect copy() {
        return new VisionSpectralSynthezoidEffect(this);
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
        VisionSpectralSynthezoidWatcher watcher = game.getState().getWatcher(VisionSpectralSynthezoidWatcher.class);
        if (controller == null
                || card == null
                || (card.isCreature(game) || !card.hasSubtype(SubType.ROBOT, game))
                || watcher == null
                || sourceObject == null
                || watcher.isAbilityUsed(new MageObjectReference(sourceObject, game))) {
            return false;
        }
        if (!controller.getHand().contains(card.getId())) {
            return false;
        }

        allowCardToPlayWithoutMana(objectId, source, affectedControllerId, MageIdentifier.VisionSpectralSynthezoidWatcher, game);
        return true;
    }
}

class VisionSpectralSynthezoidWatcher extends Watcher {

    private final Set<MageObjectReference> usedFrom = new HashSet<>();

    public VisionSpectralSynthezoidWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST
                && event.hasApprovingIdentifier(MageIdentifier.VisionSpectralSynthezoidWatcher)) {
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
