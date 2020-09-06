package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class ShadowsOfThePast extends CardImpl {

    public ShadowsOfThePast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // Whenever a creature dies, scry 1.
        this.addAbility(new DiesCreatureTriggeredAbility(new ScryEffect(1), false));

        // {4}{B}: Each opponent loses 2 life and you gain 2 life. Activate this ability only if there are four or more creature cards in your graveyard.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                new LoseLifeOpponentsEffect(2), new ManaCostsImpl<>("{4}{B}"), new CardsInControllerGraveyardCondition(4, StaticFilters.FILTER_CARD_CREATURE));
        Effect effect = new GainLifeEffect(2);
        effect.setText("and you gain 2 life");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public ShadowsOfThePast(final ShadowsOfThePast card) {
        super(card);
    }

    @Override
    public ShadowsOfThePast copy() {
        return new ShadowsOfThePast(this);
    }
}
