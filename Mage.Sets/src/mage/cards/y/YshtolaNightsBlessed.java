package mage.cards.y;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.UUID;

/**
 * @author inuenc
 */
public final class YshtolaNightsBlessed extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a noncreature spell with mana value 3 or greater");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 2));
    }

    public YshtolaNightsBlessed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // At the beginning of each end step, if a player lost 4 or more life this turn, you draw a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.ANY, new DrawCardSourceControllerEffect(1, true),
                false, YshtolaNightsBlessedCondition.instance
        ).addHint(YshtolaNightsBlessedCondition.getHint()));

        // Whenever you cast a noncreature spell with mana value 3 or greater, Y'shtola deals 2 damage to each opponent and you gain 2 life.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DamagePlayersEffect(2, TargetController.OPPONENT), filter, false
        );
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);
    }

    private YshtolaNightsBlessed(final YshtolaNightsBlessed card) {
        super(card);
    }

    @Override
    public YshtolaNightsBlessed copy() {
        return new YshtolaNightsBlessed(this);
    }
}

enum YshtolaNightsBlessedCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        if (watcher == null) {
            return false;
        }
        return watcher != null
                && game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .anyMatch(uuid -> watcher.getLifeLost(uuid) >= 4);
    }

    @Override
    public String toString() {
        return "a player lost 4 or more life this turn";
    }
}
