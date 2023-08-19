package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.token.ClueArtifactToken;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.TreasureToken;
import mage.game.stack.Spell;
import mage.watchers.common.SpellsCastWatcher;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class FaeOffering extends CardImpl {

    public FaeOffering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // At the beginning of each end step, if you've cast both a creature spell and a noncreature spell this turn, create a Clue token, a Food token, and a Treasure token.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new CreateTokenEffect(new ClueArtifactToken()), TargetController.ANY, false
                ), FaeOfferingCondition.instance, "At the beginning of each end step, " +
                "if you've cast both a creature spell and a noncreature spell this turn, " +
                "create a Clue token, a Food token, and a Treasure token."
        );
        ability.addEffect(new CreateTokenEffect(new FoodToken()));
        ability.addEffect(new CreateTokenEffect(new TreasureToken()));
        this.addAbility(ability.addHint(FaeOfferingHint.instance));
    }

    private FaeOffering(final FaeOffering card) {
        super(card);
    }

    @Override
    public FaeOffering copy() {
        return new FaeOffering(this);
    }
}

enum FaeOfferingCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher == null) {
            return false;
        }
        List<Spell> spells = watcher.getSpellsCastThisTurn(source.getControllerId());
        return spells != null && spells
                .stream()
                .filter(Objects::nonNull)
                .map(spell -> spell.isCreature(game))
                .distinct()
                .count() == 2;
    }
}

enum FaeOfferingHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher == null) {
            return null;
        }
        List<Spell> spells = watcher.getSpellsCastThisTurn(ability.getControllerId());
        if (spells == null) {
            return null;
        }
        List<String> messages = spells
                .stream()
                .filter(Objects::nonNull)
                .map(spell -> spell.isCreature(game))
                .distinct()
                .map(b -> b ? "Creature spell" : "Noncreature spell")
                .sorted()
                .collect(Collectors.toList());
        if (messages.isEmpty()) {
            return "You have not cast any spells this turn";
        }
        return "You have cast a " + String.join(" and a ", messages) + " this turn";
    }

    @Override
    public FaeOfferingHint copy() {
        return instance;
    }
}
