package mage.cards.a;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author MarcoMarin
 */
public final class ArtifactWard extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("artifact creatures");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public ArtifactWard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Protect));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature can't be blocked by artifact creatures.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByCreaturesAttachedEffect(
                Duration.WhileOnBattlefield, filter, AttachmentType.AURA)));

        // Prevent all damage that would be dealt to enchanted creature by artifact sources.
        this.addAbility(new SimpleStaticAbility(new ArtifactWardPreventDamageEffect()));

        // Enchanted creature can't be the target of abilities from artifact sources.
        this.addAbility(new SimpleStaticAbility(new ArtifactWardCantBeTargetedEffect()));
    }

    private ArtifactWard(final ArtifactWard card) {
        super(card);
    }

    @Override
    public ArtifactWard copy() {
        return new ArtifactWard(this);
    }
}

class ArtifactWardPreventDamageEffect extends PreventionEffectImpl {

    ArtifactWardPreventDamageEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false);
        staticText = "Prevent all damage that would be dealt to enchanted creature by artifact sources";
    }

    private ArtifactWardPreventDamageEffect(final ArtifactWardPreventDamageEffect effect) {
        super(effect);
    }

    @Override
    public ArtifactWardPreventDamageEffect copy() {
        return new ArtifactWardPreventDamageEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!super.applies(event, source, game)) {
            return false;
        }
        Permanent attachment = game.getPermanent(source.getSourceId());
        if (attachment == null || attachment.getAttachedTo() == null
                || !event.getTargetId().equals(attachment.getAttachedTo())) {
            return false;
        }
        MageObject damageSource = game.getObject(event.getSourceId());
        return damageSource != null && damageSource.isArtifact(game);
    }
}

/**
 * Abilities only: a spell is not an ability, so an artifact spell may still
 * target the enchanted creature (unlike protection from artifacts).
 */
class ArtifactWardCantBeTargetedEffect extends ContinuousRuleModifyingEffectImpl {

    ArtifactWardCantBeTargetedEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Enchanted creature can't be the target of abilities from artifact sources";
    }

    private ArtifactWardCantBeTargetedEffect(final ArtifactWardCantBeTargetedEffect effect) {
        super(effect);
    }

    @Override
    public ArtifactWardCantBeTargetedEffect copy() {
        return new ArtifactWardCantBeTargetedEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGET;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        if (attachment == null || attachment.getAttachedTo() == null
                || !event.getTargetId().equals(attachment.getAttachedTo())) {
            return false;
        }
        MageObject object = game.getObject(event.getSourceId());
        if (object == null || object instanceof Spell) {
            return false;
        }
        MageObject abilitySource = object instanceof StackAbility
                ? ((StackAbility) object).getSourceObject(game)
                : object;
        return abilitySource != null && abilitySource.isArtifact(game);
    }
}
