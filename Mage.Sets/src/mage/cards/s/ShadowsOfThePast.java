package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ShadowsOfThePast extends CardImpl {

    private static final Condition condition = new CardsInControllerGraveyardCondition(4, StaticFilters.FILTER_CARD_CREATURES);

    public ShadowsOfThePast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // Whenever a creature dies, scry 1.
        this.addAbility(new DiesCreatureTriggeredAbility(new ScryEffect(1), false));

        // {4}{B}: Each opponent loses 2 life and you gain 2 life. Activate this ability only if there are four or more creature cards in your graveyard.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new LoseLifeOpponentsEffect(2), new ManaCostsImpl<>("{4}{B}"), condition
        );
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);
    }

    private ShadowsOfThePast(final ShadowsOfThePast card) {
        super(card);
    }

    @Override
    public ShadowsOfThePast copy() {
        return new ShadowsOfThePast(this);
    }
}
