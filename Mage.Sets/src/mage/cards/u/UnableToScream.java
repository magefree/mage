package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.AddCardTypeAttachedEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesAttachedEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author markort147
 */
public final class UnableToScream extends CardImpl {

    public UnableToScream(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature loses all abilities and is a Toy artifact creature with base power and toughness 0/2 in addition to its other types.
        Ability ability = new SimpleStaticAbility(new LoseAllAbilitiesAttachedEffect(AttachmentType.AURA));
        ability.addEffect(new AddCardSubtypeAttachedEffect(SubType.TOY, AttachmentType.AURA).setText(" and is a Toy"));
        ability.addEffect(new AddCardTypeAttachedEffect(CardType.ARTIFACT, AttachmentType.AURA).setText(" artifact creature"));
        ability.addEffect(new SetBasePowerToughnessAttachedEffect(0, 2, AttachmentType.AURA).setText(" with base power and toughness 0/2 in addition to its other types."));
        this.addAbility(ability);

        // As long as enchanted creature is face down, it can't be turned face up.
        this.addAbility(new SimpleStaticAbility(new UnableToScreamPreventingEffect()));
    }

    private UnableToScream(final UnableToScream card) {
        super(card);
    }

    @Override
    public UnableToScream copy() {
        return new UnableToScream(this);
    }
}

class UnableToScreamPreventingEffect extends ContinuousRuleModifyingEffectImpl {

    UnableToScreamPreventingEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        setText("As long as enchanted creature is face down, it can't be turned face up.");
    }

    private UnableToScreamPreventingEffect(final UnableToScreamPreventingEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType().equals(GameEvent.EventType.TURN_FACE_UP);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent aura = game.getPermanent(source.getSourceId());
        if (aura == null) {
            return false;
        }

        Permanent creature = game.getPermanent(aura.getAttachedTo());
        if (creature == null) {
            return false;
        }

        return creature.isFaceDown(game) && event.getTargetId().equals(creature.getId());
    }

    @Override
    public UnableToScreamPreventingEffect copy() {
        return new UnableToScreamPreventingEffect(this);
    }
}
