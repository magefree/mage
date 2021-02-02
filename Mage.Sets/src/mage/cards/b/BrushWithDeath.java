package mage.cards.b;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class BrushWithDeath extends CardImpl {

    public BrushWithDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Buyback {2}{B}{B}
        this.addAbility(new BuybackAbility("{2}{B}{B}"));

        // Target opponent loses 2 life. You gain 2 life.
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(2));
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private BrushWithDeath(final BrushWithDeath card) {
        super(card);
    }

    @Override
    public BrushWithDeath copy() {
        return new BrushWithDeath(this);
    }
}
