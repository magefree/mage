package mage.cards.l;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.CompanionAbility;
import mage.abilities.keyword.CompanionCondition;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

/**
 * @author TheElk801
 */
public final class LurrusOfTheDreamDen extends CardImpl {

    public LurrusOfTheDreamDen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W/B}{W/B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Companion — Each permanent card in your starting deck has converted mana cost 2 or less.
        this.addAbility(new CompanionAbility(LurrusOfTheDreamDenCompanionCondition.instance));

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // During each of your turns, you may cast one permanent spell with converted mana cost 2 or less from your graveyard.
        this.addAbility(new SimpleStaticAbility(
                new LurrusOfTheDreamDenContinuousEffect()
        ), new LurrusOfTheDreamDenWatcher());
    }

    private LurrusOfTheDreamDen(final LurrusOfTheDreamDen card) {
        super(card);
    }

    @Override
    public LurrusOfTheDreamDen copy() {
        return new LurrusOfTheDreamDen(this);
    }
}

enum LurrusOfTheDreamDenCompanionCondition implements CompanionCondition {
    instance;

    @Override
    public String getRule() {
        return "Each permanent card in your starting deck has converted mana cost 2 or less.";
    }

    @Override
    public boolean isLegal(Set<Card> deck, int startingSize) {
        return deck.stream()
                .filter(MageObject::isPermanent)
                .mapToInt(MageObject::getConvertedManaCost)
                .max()
                .orElse(0) <= 2;
    }
}

class LurrusOfTheDreamDenContinuousEffect extends ContinuousEffectImpl {

    private static final FilterCard filter
            = new FilterPermanentCard("permanent spell with converted mana cost 2 or less from your graveyard");

    static {
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 3));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    LurrusOfTheDreamDenContinuousEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "During each of your turns, you may cast one permanent spell "
                + "with converted mana cost 2 or less from your graveyard";
    }

    private LurrusOfTheDreamDenContinuousEffect(final LurrusOfTheDreamDenContinuousEffect effect) {
        super(effect);
    }

    @Override
    public LurrusOfTheDreamDenContinuousEffect copy() {
        return new LurrusOfTheDreamDenContinuousEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (game.isActivePlayer(player.getId())) {
            LurrusOfTheDreamDenWatcher watcher = game.getState().getWatcher(LurrusOfTheDreamDenWatcher.class);
            if (watcher != null && !watcher.isAbilityUsed(new MageObjectReference(source.getSourceId(), game))) {
                // if not used yet, add effect per card in graveyard to cast it
                for (Card card : player.getGraveyard().getCards(filter, game)) {
                    ContinuousEffect effect = new LurrusOfTheDreamDenCastFromGraveyardEffect();
                    effect.setTargetPointer(new FixedTarget(card, game));
                    game.addEffect(effect, source);
                }
            }
        }
        return true;
    }
}

class LurrusOfTheDreamDenCastFromGraveyardEffect extends AsThoughEffectImpl {

    LurrusOfTheDreamDenCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may cast one permanent spell with converted mana cost 2 or less from your graveyard";
    }

    private LurrusOfTheDreamDenCastFromGraveyardEffect(final LurrusOfTheDreamDenCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public LurrusOfTheDreamDenCastFromGraveyardEffect copy() {
        return new LurrusOfTheDreamDenCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!objectId.equals(getTargetPointer().getFirst(game, source))) {
            return false;
        }
        if (!affectedControllerId.equals(source.getControllerId())) {
            return false;
        }
        LurrusOfTheDreamDenWatcher watcher = game.getState().getWatcher(LurrusOfTheDreamDenWatcher.class);
        return watcher != null && !watcher.isAbilityUsed(new MageObjectReference(source.getSourceId(), game));
    }
}

class LurrusOfTheDreamDenWatcher extends Watcher {

    private final Set<MageObjectReference> abilityUsed = new HashSet<>();

    LurrusOfTheDreamDenWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST
                || event.getZone() != Zone.GRAVEYARD
                || event.getAdditionalReference() == null) { // permitting source to cast from graveyard
            return;
        }
        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null || !spell.isPermanent() || spell.getConvertedManaCost() > 2) {
            return;
        }
        abilityUsed.add(event.getAdditionalReference());
    }

    @Override
    public void reset() {
        super.reset();
        abilityUsed.clear();
    }

    boolean isAbilityUsed(MageObjectReference mor) {
        return abilityUsed.contains(mor);
    }
}
