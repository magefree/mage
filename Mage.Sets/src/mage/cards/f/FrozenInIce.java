package mage.cards.f;

import java.util.Optional;
import java.util.UUID;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;

/**
 *
 * @author muz
 */
public final class FrozenInIce extends CardImpl {

    public FrozenInIce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When this Aura enters, tap enchanted creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TapEnchantedEffect()));

        // Enchanted creature loses all abilities and can't become untapped.
        Ability ability = new SimpleStaticAbility(new FrozenInIceAbilityEffect());
        ability.addEffect(new FrozenInIceUntapEffect().concatBy("and"));
        this.addAbility(ability);
    }

    private FrozenInIce(final FrozenInIce card) {
        super(card);
    }

    @Override
    public FrozenInIce copy() {
        return new FrozenInIce(this);
    }
}

class FrozenInIceAbilityEffect extends ContinuousEffectImpl {

    FrozenInIceAbilityEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.Benefit);
        staticText = "enchanted creature loses all abilities";
    }

    private FrozenInIceAbilityEffect(final FrozenInIceAbilityEffect effect) {
        super(effect);
    }

    @Override
    public FrozenInIceAbilityEffect copy() {
        return new FrozenInIceAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .ifPresent(permanent -> permanent.removeAllAbilities(source.getSourceId(), game));
        return true;
    }
}

class FrozenInIceUntapEffect extends ReplacementEffectImpl {

    FrozenInIceUntapEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Tap);
        staticText = "can't become untapped";
    }

    private FrozenInIceUntapEffect(final FrozenInIceUntapEffect effect) {
        super(effect);
    }

    @Override
    public FrozenInIceUntapEffect copy() {
        return new FrozenInIceUntapEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return game.getPermanent(event.getTargetId()) != null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.getSourceId().equals(event.getTargetId());
    }
}
