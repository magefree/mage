package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.game.permanent.token.ZombieToken;

/**
 * @author Loki
 */
public final class ReapTheSeagraf extends CardImpl {

    public ReapTheSeagraf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        this.getSpellAbility().addEffect(new CreateTokenEffect(new ZombieToken()));
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{4}{U}")));
    }

    private ReapTheSeagraf(final ReapTheSeagraf card) {
        super(card);
    }

    @Override
    public ReapTheSeagraf copy() {
        return new ReapTheSeagraf(this);
    }
}
