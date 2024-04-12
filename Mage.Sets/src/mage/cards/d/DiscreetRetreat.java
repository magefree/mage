package mage.cards.d;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.predicate.mageobject.OutlawPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;
import mage.watchers.common.SpellsCastWatcher;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class DiscreetRetreat extends CardImpl {

    public DiscreetRetreat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted land has "{T}: Add two mana of any one color. Spend this mana only to cast outlaw spells or activate abilities from outlaw sources."
        Ability gainedAbility = new ConditionalAnyColorManaAbility(
                new TapSourceCost(), 2, new DiscreetRetreatManaBuilder()
        );
        Effect effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.AURA);
        effect.setText("Enchanted land has \"{T}: Add two mana of any one color. "
                + "Spend this mana only to cast outlaw spells or activate abilities from outlaw sources.\"");
        this.addAbility(new SimpleStaticAbility(effect));

        // Whenever you cast your first outlaw spell each turn, you draw a card and you lose 1 life.
        this.addAbility(new DiscreetRetreatTriggeredAbility());
    }

    private DiscreetRetreat(final DiscreetRetreat card) {
        super(card);
    }

    @Override
    public DiscreetRetreat copy() {
        return new DiscreetRetreat(this);
    }
}

class DiscreetRetreatTriggeredAbility extends TriggeredAbilityImpl {

    DiscreetRetreatTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        addEffect(new LoseLifeSourceControllerEffect(1));
        setTriggerPhrase("Whenever you cast your first outlaw spell each turn, ");
    }

    private DiscreetRetreatTriggeredAbility(final DiscreetRetreatTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DiscreetRetreatTriggeredAbility copy() {
        return new DiscreetRetreatTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(this.getControllerId())) {
            return false;
        }
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher == null) {
            return false;
        }
        List<Spell> outlawSpells = watcher
                .getSpellsCastThisTurn(this.getControllerId())
                .stream()
                .filter(Objects::nonNull)
                .filter(s -> OutlawPredicate.instance.apply(s, game))
                .collect(Collectors.toList());
        return outlawSpells.size() == 1 && outlawSpells.get(0).getId().equals(event.getTargetId());
    }
}

class DiscreetRetreatManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new DiscreetRetreatConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast outlaw spells or activate abilities from outlaw sources";
    }
}

class DiscreetRetreatConditionalMana extends ConditionalMana {
    public DiscreetRetreatConditionalMana(Mana mana) {
        super(mana);
        addCondition(DiscreetRetreatCondition.instance);
    }
}

enum DiscreetRetreatCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return object != null && OutlawPredicate.instance.apply(object, game);
    }
}
