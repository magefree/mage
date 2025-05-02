package mage.cards.a;

import mage.Mana;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.delayed.ManaSpentDelayedTriggeredAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.costs.common.ExertSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ArenaOfGlory extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.MOUNTAIN);
    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public ArenaOfGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Arena of Glory enters the battlefield tapped unless you control a Mountain.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

        // {R}, {T}, Exert Arena of Glory: Add {R}{R}. If that mana is spent on a creature spell, it gains haste until end of turn.
        SimpleManaAbility ability = new SimpleManaAbility(
                new BasicManaEffect(Mana.RedMana(2)),
                new ManaCostsImpl<>("{R}")
        );
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(
                new ArenaOfGloryDelayedTriggeredAbility()
        ));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExertSourceCost());
        this.addAbility(ability);
    }

    private ArenaOfGlory(final ArenaOfGlory card) {
        super(card);
    }

    @Override
    public ArenaOfGlory copy() {
        return new ArenaOfGlory(this);
    }
}

class ArenaOfGloryDelayedTriggeredAbility extends ManaSpentDelayedTriggeredAbility {

    ArenaOfGloryDelayedTriggeredAbility() {
        super(
                new AddContinuousEffectToGame(
                        new GainAbilityTargetEffect(
                                HasteAbility.getInstance(), Duration.EndOfTurn,
                                "it gains haste until end of turn", true
                        )
                ),
                StaticFilters.FILTER_SPELL_CREATURE
        );
        this.usesStack = false;
        this.triggerOnlyOnce = false;
        setTriggerPhrase("If that mana is spent on a creature spell, ");
    }

    private ArenaOfGloryDelayedTriggeredAbility(final ArenaOfGloryDelayedTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public ArenaOfGloryDelayedTriggeredAbility copy() {
        return new ArenaOfGloryDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(spell.getCard(), game));
        return true;
    }
}