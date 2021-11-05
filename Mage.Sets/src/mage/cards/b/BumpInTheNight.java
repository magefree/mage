package mage.cards.b;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class BumpInTheNight extends CardImpl {

    public BumpInTheNight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Target opponent loses 3 life.
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Flashback {5}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{5}{R}")));
    }

    private BumpInTheNight(final BumpInTheNight card) {
        super(card);
    }

    @Override
    public BumpInTheNight copy() {
        return new BumpInTheNight(this);
    }
}
