package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
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
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature loses all abilities and is a Toy artifact creature with base power and toughness 0/2
        // in addition to its other types.
        this.addAbility(new SimpleStaticAbility(new UnableToScreamBecomesEffect()));

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

class UnableToScreamBecomesEffect extends ContinuousEffectImpl {

    UnableToScreamBecomesEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        setText("Enchanted creature loses all abilities and is " +
                "a Toy artifact creature with base power and " +
                "toughness 0/2 in addition to its other types.");
    }

    private UnableToScreamBecomesEffect(final UnableToScreamBecomesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent aura = game.getPermanent(source.getSourceId());
        if (aura == null)
            return false;

        Permanent creature = game.getPermanent(aura.getAttachedTo());
        if (creature == null)
            return false;

        creature.getPower().setModifiedBaseValue(0);
        creature.getToughness().setModifiedBaseValue(2);
        creature.removeAllAbilities(source.getSourceId(), game);
        creature.getCardType().add(CardType.ARTIFACT);
        creature.getSubtype().add(SubType.TOY);

        return true;
    }

    @Override
    public ContinuousEffect copy() {
        return new UnableToScreamBecomesEffect(this);
    }
}

class UnableToScreamPreventingEffect extends ContinuousRuleModifyingEffectImpl {

    UnableToScreamPreventingEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
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
        if (aura == null)
            return false;

        Permanent creature = game.getPermanent(aura.getAttachedTo());
        if (creature == null)
            return false;

        return creature.isFaceDown(game) && event.getTargetId().equals(creature.getId());
    }

    @Override
    public ContinuousEffect copy() {
        return new UnableToScreamPreventingEffect(this);
    }
}
