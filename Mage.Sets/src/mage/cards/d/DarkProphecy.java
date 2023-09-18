package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DarkProphecy extends CardImpl {

    public DarkProphecy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}{B}");

        // Whenever a creature you control dies, you draw a card and you lose 1 life.
        Effect effect = new DrawCardSourceControllerEffect(1, "you");
        Ability ability = new DiesCreatureTriggeredAbility(effect, false, StaticFilters.FILTER_CONTROLLED_A_CREATURE);
        effect = new LoseLifeSourceControllerEffect(1);
        ability.addEffect(effect.concatBy("and"));
        this.addAbility(ability);
    }

    private DarkProphecy(final DarkProphecy card) {
        super(card);
    }

    @Override
    public DarkProphecy copy() {
        return new DarkProphecy(this);
    }
}
