package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.constants.*;
import mage.abilities.effects.common.AttachEffect;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 *
 * @author weirddan455
 */
public final class AwakenedAwareness extends CardImpl {

    public AwakenedAwareness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{X}{U}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant artifact or creature
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // When Awakened Awareness enters the battlefield, put X +1/+1 counters on enchanted permanent.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new AddCountersAttachedEffect(CounterType.P1P1.createInstance(), ManacostVariableValue.ETB, "enchanted permanent")
        ));

        // As long as enchanted permanent is a creature, it has base power and toughness 1/1.
        this.addAbility(new SimpleStaticAbility(new AwakenedAwarenessEffect()));
    }

    private AwakenedAwareness(final AwakenedAwareness card) {
        super(card);
    }

    @Override
    public AwakenedAwareness copy() {
        return new AwakenedAwareness(this);
    }
}

class AwakenedAwarenessEffect extends ContinuousEffectImpl {

    public AwakenedAwarenessEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.UnboostCreature);
        this.staticText = "As long as enchanted permanent is a creature, it has base power and toughness 1/1";
    }

    private AwakenedAwarenessEffect(final AwakenedAwarenessEffect effect) {
        super(effect);
    }

    @Override
    public AwakenedAwarenessEffect copy() {
        return new AwakenedAwarenessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = source.getSourcePermanentIfItStillExists(game);
        if (enchantment != null) {
            Permanent creature = game.getPermanent(enchantment.getAttachedTo());
            if (creature != null && creature.isCreature(game)) {
                creature.getPower().setValue(1);
                creature.getToughness().setValue(1);
                return true;
            }
        }
        return false;
    }
}
