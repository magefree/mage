
package mage.cards.r;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class Riddleform extends CardImpl {

    public Riddleform(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // Whenever you cast a noncreature spell, you may have Riddleform become a 3/3 Sphinx creature with flying in addition to its other types until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(3, 3, "3/3 Sphinx creature with flying", SubType.SPHINX).withAbility(FlyingAbility.getInstance()),
                CardType.ENCHANTMENT,
                Duration.EndOfTurn
            ),
            StaticFilters.FILTER_SPELL_A_NON_CREATURE,
            true
        ));

        // {2}{U}: Scry 1.
        this.addAbility(new SimpleActivatedAbility(new ScryEffect(1), new ManaCostsImpl<>("{2}{U}")));
    }

    private Riddleform(final Riddleform card) {
        super(card);
    }

    @Override
    public Riddleform copy() {
        return new Riddleform(this);
    }
}
