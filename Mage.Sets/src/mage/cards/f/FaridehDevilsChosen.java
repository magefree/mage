package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreDiceRolledTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FaridehDevilsChosen extends CardImpl {

    public FaridehDevilsChosen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIEFLING);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Dark One's Own Luck — Whenever you roll one or more dice, Farideh, Devil's Chosen gains flying and menace until end of turn. If any of those results was 10 or higher, draw a card.
        Ability ability = new OneOrMoreDiceRolledTriggeredAbility(
                new GainAbilitySourceEffect(
                        FlyingAbility.getInstance(), Duration.EndOfTurn
                ).setText("{this} gains flying")
        );
        ability.addEffect(new GainAbilitySourceEffect(
                new MenaceAbility(), Duration.EndOfTurn
        ).setText("and menace until end of turn"));
        ability.addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), FaridehDevilsChosenCondition.instance,
                "If any of those results was 10 or higher, draw a card"
        ));
        this.addAbility(ability.withFlavorWord("Dark One's Own Luck"));
    }

    private FaridehDevilsChosen(final FaridehDevilsChosen card) {
        super(card);
    }

    @Override
    public FaridehDevilsChosen copy() {
        return new FaridehDevilsChosen(this);
    }
}

enum FaridehDevilsChosenCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return source
                .getEffects()
                .stream()
                .map(effect -> effect.getValue("maxDieRoll"))
                .filter(Objects::nonNull)
                .mapToInt(Integer.class::cast)
                .anyMatch(x -> x >= 10);
    }
}
