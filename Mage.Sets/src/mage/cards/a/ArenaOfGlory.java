package mage.cards.a;

import mage.MageObjectReference;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.delayed.ManaSpentDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.ExertSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ArenaOfGlory extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.MOUNTAIN);
    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);

    public ArenaOfGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Arena of Glory enters the battlefield tapped unless you control a Mountain.
        this.addAbility(new EntersBattlefieldAbility(new ConditionalOneShotEffect(
                new TapSourceEffect(), condition
        ), "tapped unless you control a Mountain"));

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
        super(new ArenaOfGloryTargetEffect(), StaticFilters.FILTER_SPELL_CREATURE);
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
        getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
        return true;
    }
}

// Target spell and the permanent it becomes, gain haste until end of turn
class ArenaOfGloryTargetEffect extends OneShotEffect {

    ArenaOfGloryTargetEffect() {
        super(Outcome.Benefit);
        staticText = "it gains haste until end of turn";
    }

    private ArenaOfGloryTargetEffect(final ArenaOfGloryTargetEffect effect) {
        super(effect);
    }

    @Override
    public ArenaOfGloryTargetEffect copy() {
        return new ArenaOfGloryTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addEffect(new ArenaOfGloryHasteEffect(
                new MageObjectReference(getTargetPointer().getFirst(game, source), game)
        ), source);
        return true;
    }
}

// Gives haste to both the spell and permanent it becomes. Is cleared at end of turn or if the MOR doesn't find either.
class ArenaOfGloryHasteEffect extends ContinuousEffectImpl {

    private final MageObjectReference morSpell;
    private MageObjectReference morCard;

    ArenaOfGloryHasteEffect(MageObjectReference morSpell) {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.morSpell = morSpell;
    }

    private ArenaOfGloryHasteEffect(final ArenaOfGloryHasteEffect effect) {
        super(effect);
        this.morSpell = effect.morSpell;
        this.morCard = effect.morCard;
    }

    @Override
    public ArenaOfGloryHasteEffect copy() {
        return new ArenaOfGloryHasteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = morSpell.getSpell(game);
        Permanent permanent = morCard == null ? null : morCard.getPermanent(game);
        if (spell == null && permanent == null) {
            discard();
            return false;
        }
        if (spell != null) {
            Card card = spell.getCard();
            if (card == null) {
                // Spell without card seems very weird, should not happen?
                discard();
                return false;
            }
            game.getState().addOtherAbility(card, HasteAbility.getInstance());
            if (morCard == null) {
                morCard = new MageObjectReference(card, game, 1);
            }
        }
        if (permanent != null) {
            permanent.addAbility(HasteAbility.getInstance(), source.getSourceId(), game);
        }
        return true;
    }
}
