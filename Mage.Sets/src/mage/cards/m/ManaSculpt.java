package mage.cards.m;

import java.util.UUID;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfMainPhaseDelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfMainPhaseDelayedTriggeredAbility.PhaseSelection;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.AddManaToManaPoolTargetControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetSpell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author muz
 */
public final class ManaSculpt extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.WIZARD);
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "You control a Wizard");

    public ManaSculpt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Counter target spell. If you control a Wizard, add an amount of {C} equal to the amount of mana spent to cast that spell at the beginning of your next main phase.
        this.getSpellAbility().addEffect(new ManaSculptEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addHint(hint);
    }

    private ManaSculpt(final ManaSculpt card) {
        super(card);
    }

    @Override
    public ManaSculpt copy() {
        return new ManaSculpt(this);
    }

    private static final class ManaSculptEffect extends OneShotEffect {

        ManaSculptEffect() {
            super(Outcome.Benefit);
            this.staticText = "counter target spell. If you control a Wizard, add an amount of {C} equal to the amount of mana spent to cast that spell at the beginning of your next main phase";
        }

        private ManaSculptEffect(final ManaSculptEffect effect) {
            super(effect);
        }

        @Override
        public ManaSculptEffect copy() {
            return new ManaSculptEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
            if (spell == null) {
                return false;
            }

            game.getStack().counter(spell.getId(), source, game);

            if (game.getBattlefield().contains(filter, source.getControllerId(), source, game, 1)) {
                int cmc = spell.getManaValue();
                Effect effect = new AddManaToManaPoolTargetControllerEffect(Mana.ColorlessMana(cmc), "your");
                effect.setTargetPointer(new FixedTarget(source.getControllerId()));
                AtTheBeginOfMainPhaseDelayedTriggeredAbility delayedAbility
                        = new AtTheBeginOfMainPhaseDelayedTriggeredAbility(effect, false, TargetController.YOU, PhaseSelection.NEXT_MAIN);
                game.addDelayedTriggeredAbility(delayedAbility, source);
            }
            return true;
        }
    }
}
