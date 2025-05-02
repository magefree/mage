package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.constants.*;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.watchers.common.PlayerLostLifeWatcher;

/**
 *
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
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        TargetController.ANY,
                        new DrawCardSourceControllerEffect(1),
                        false
                ), YshtolaNightsBlessedCondition.instance, "At the beginning of each end step, " +
                "if a player lost 4 or more life this turn, you draw a card."
        ).addHint(new ConditionHint(YshtolaNightsBlessedCondition.instance, "A player lost 4 or more life this turn")));

        // Whenever you cast a noncreature spell with mana value 3 or greater, Y'shtola deals 2 damage to each opponent and you gain 2 life.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new DamagePlayersEffect(
                2, TargetController.OPPONENT, "Y'shtola"),
                filter, false
        );
        ability.addEffect(new GainLifeEffect(2).setText("and you gain 2 life"));
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

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        if (watcher == null) {
            return false;
        }
        return game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .anyMatch(uuid -> watcher.getLifeLost(uuid) > 3);
    }
}
