
package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author TheElk801
 */
public final class ScorchingMissile extends CardImpl {

    public ScorchingMissile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Scorching Missile deals 4 damage to target player.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());

        // Flashback {9}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{9}{R}")));

    }

    private ScorchingMissile(final ScorchingMissile card) {
        super(card);
    }

    @Override
    public ScorchingMissile copy() {
        return new ScorchingMissile(this);
    }
}
