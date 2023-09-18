
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TimingRule;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class CripplingFatigue extends CardImpl {

    public CripplingFatigue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}{B}");

        // Target creature gets -2/-2 until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(-2, -2, Duration.EndOfTurn));

        // Flashback-{1}{B}, Pay 3 life
        Ability ability = new FlashbackAbility(this, new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new PayLifeCost(3));
        this.addAbility(ability);
    }

    private CripplingFatigue(final CripplingFatigue card) {
        super(card);
    }

    @Override
    public CripplingFatigue copy() {
        return new CripplingFatigue(this);
    }
}
