package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Blossombind extends CardImpl {

    public Blossombind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When this Aura enters, tap enchanted creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TapEnchantedEffect()));

        // Enchanted creature can't become untapped and can't have counters put on it.
        Ability ability = new SimpleStaticAbility(new BlossombindUntapEffect());
        ability.addEffect(new BlossombindCounterEffect());
        this.addAbility(ability);
    }

    private Blossombind(final Blossombind card) {
        super(card);
    }

    @Override
    public Blossombind copy() {
        return new Blossombind(this);
    }
}

class BlossombindUntapEffect extends ReplacementEffectImpl {

    BlossombindUntapEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Tap);
        staticText = "enchanted creature can't become untapped";
    }

    private BlossombindUntapEffect(final BlossombindUntapEffect effect) {
        super(effect);
    }

    @Override
    public BlossombindUntapEffect copy() {
        return new BlossombindUntapEffect(this);
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

class BlossombindCounterEffect extends ContinuousEffectImpl {

    BlossombindCounterEffect() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Detriment);
        staticText = "and can't have counters put on it";
    }

    private BlossombindCounterEffect(final BlossombindCounterEffect effect) {
        super(effect);
    }

    @Override
    public BlossombindCounterEffect copy() {
        return new BlossombindCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .ifPresent(permanent -> permanent.setCountersCanBeAdded(false));
        return true;
    }
}
