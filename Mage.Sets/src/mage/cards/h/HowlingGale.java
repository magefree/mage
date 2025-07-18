
package mage.cards.h;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HowlingGale extends CardImpl {

    public HowlingGale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Howling Gale deals 1 damage to each creature with flying and each player.
        this.getSpellAbility().addEffect(new DamageAllEffect(1, StaticFilters.FILTER_CREATURE_FLYING));
        Effect effect = new DamagePlayersEffect(1);
        effect.setText("and each player");
        this.getSpellAbility().addEffect(effect);

        // Flashback {1}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{1}{G}")));
    }

    private HowlingGale(final HowlingGale card) {
        super(card);
    }

    @Override
    public HowlingGale copy() {
        return new HowlingGale(this);
    }
}
