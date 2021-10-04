
package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AddCombatAndMainPhaseEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SeizeTheDay extends CardImpl {

    public SeizeTheDay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");

        // Untap target creature. After this main phase, there is an additional combat phase followed by an additional main phase.
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new AddCombatAndMainPhaseEffect());

        // Flashback {2}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{2}{R}")));
    }

    private SeizeTheDay(final SeizeTheDay card) {
        super(card);
    }

    @Override
    public SeizeTheDay copy() {
        return new SeizeTheDay(this);
    }
}
