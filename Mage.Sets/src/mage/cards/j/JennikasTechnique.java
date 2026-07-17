package mage.cards.j;

import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.SneakAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JennikasTechnique extends CardImpl {

    public JennikasTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Sneak {R}
        this.addAbility(new SneakAbility(this, "{R}"));

        // Jennika's Technique deals 2 damage to each creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(2, StaticFilters.FILTER_PERMANENT_CREATURE));
    }

    private JennikasTechnique(final JennikasTechnique card) {
        super(card);
    }

    @Override
    public JennikasTechnique copy() {
        return new JennikasTechnique(this);
    }
}
