
package mage.cards.a;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class AbsorbVis extends CardImpl {

    public AbsorbVis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{B}");

        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(4));
        this.getSpellAbility().addEffect(new GainLifeEffect(4).setText("and you gain 4 life"));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{1}{B}")));
    }

    private AbsorbVis(final AbsorbVis card) {
        super(card);
    }

    @Override
    public AbsorbVis copy() {
        return new AbsorbVis(this);
    }

}
