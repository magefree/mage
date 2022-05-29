
package mage.cards.d;

import java.util.UUID;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.TargetPlayer;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public final class DeepAnalysis extends CardImpl {

    public DeepAnalysis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Target player draws two cards.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Flashback-{1}{U}, Pay 3 life.
        FlashbackAbility ability = new FlashbackAbility(this, new ManaCostsImpl<>("{1}{U}"));
        ability.addCost(new PayLifeCost(3));
        this.addAbility(ability);
    }

    private DeepAnalysis(final DeepAnalysis card) {
        super(card);
    }

    @Override
    public DeepAnalysis copy() {
        return new DeepAnalysis(this);
    }
}
