
package mage.cards.c;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ClawsOfWirewood extends CardImpl {

    public ClawsOfWirewood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");


        // Claws of Wirewood deals 3 damage to each creature with flying and each player.
        this.getSpellAbility().addEffect(new DamageAllEffect(3, StaticFilters.FILTER_CREATURE_FLYING));
        Effect effect = new DamagePlayersEffect(3);
        effect.setText("and each player");
        this.getSpellAbility().addEffect(effect);

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private ClawsOfWirewood(final ClawsOfWirewood card) {
        super(card);
    }

    @Override
    public ClawsOfWirewood copy() {
        return new ClawsOfWirewood(this);
    }
}
