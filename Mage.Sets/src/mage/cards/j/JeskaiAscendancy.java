package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class JeskaiAscendancy extends CardImpl {

    public JeskaiAscendancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{R}{W}");

        // Whenever you cast a noncreature spell, creatures you control get +1/+1 until end of turn. Untap those creatures.
        Effect effect = new BoostControlledEffect(1, 1, Duration.EndOfTurn);
        Ability ability = new SpellCastControllerTriggeredAbility(effect, StaticFilters.FILTER_SPELL_A_NON_CREATURE, false);
        effect = new UntapAllControllerEffect(StaticFilters.FILTER_PERMANENT_CREATURE, "Untap those creatures");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Whenever you cast a noncreature spell, you may draw a card. If you do, discard a card.
        effect = new DrawDiscardControllerEffect(1, 1);
        effect.setText("you may draw a card. If you do, discard a card");
        ability = new SpellCastControllerTriggeredAbility(effect, StaticFilters.FILTER_SPELL_A_NON_CREATURE, true);
        this.addAbility(ability);
    }

    private JeskaiAscendancy(final JeskaiAscendancy card) {
        super(card);
    }

    @Override
    public JeskaiAscendancy copy() {
        return new JeskaiAscendancy(this);
    }
}
