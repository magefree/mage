package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CherishedHatchling extends CardImpl {

    private static final FilterCard filterCard = new FilterCard("Dinosaur spells");

    static {
        filterCard.add(SubType.DINOSAUR.getPredicate());
    }

    public CherishedHatchling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Cherished Hatchling dies, you may cast Dinosaur spells this turn as though they had flash, and whenever you cast a Dinosaur spell this turn, it gains "When this creature enters the battlefield, you may have it fight another target creature."
        Ability ability = new DiesSourceTriggeredAbility(new CastAsThoughItHadFlashAllEffect(Duration.EndOfTurn, filterCard, false));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new CherishedHatchlingTriggeredAbility()).concatBy(", and"));
        this.addAbility(ability);
    }

    private CherishedHatchling(final CherishedHatchling card) {
        super(card);
    }

    @Override
    public CherishedHatchling copy() {
        return new CherishedHatchling(this);
    }
}

class CherishedHatchlingTriggeredAbility extends DelayedTriggeredAbility {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public CherishedHatchlingTriggeredAbility() {
        super(getEffectToAdd(), Duration.EndOfTurn, false);
        setTriggerPhrase("whenever you cast a Dinosaur spell this turn, ");
    }

    private static Effect getEffectToAdd() {
        Ability abilityToAdd = new EntersBattlefieldTriggeredAbility(new FightTargetSourceEffect().setText("you may have it fight another target creature"), true);
        abilityToAdd.addTarget(new TargetCreaturePermanent(filter));
        Effect effect = new GainAbilityTargetEffect(abilityToAdd, Duration.EndOfTurn,
                "it gains \"When this creature enters the battlefield, you may have it fight another target creature.\"", true);
        return effect;
    }

    private CherishedHatchlingTriggeredAbility(final CherishedHatchlingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CherishedHatchlingTriggeredAbility copy() {
        return new CherishedHatchlingTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.isCreature(game) && spell.hasSubtype(SubType.DINOSAUR, game)) {
                getEffects().setTargetPointer(new FixedTarget(spell.getSourceId()));
                return true;
            }
        }
        return false;
    }
}
