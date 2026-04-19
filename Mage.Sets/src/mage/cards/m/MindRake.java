package mage.cards.m;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MindRake extends CardImpl {

    public MindRake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target player discards two cards.
        // Overload {1}{B}
        OverloadAbility.implementOverloadAbility(this, new ManaCostsImpl<>("{1}{B}"),
                new TargetPlayer(), new DiscardTargetEffect(2));
    }

    private MindRake(final MindRake card) {
        super(card);
    }

    @Override
    public MindRake copy() {
        return new MindRake(this);
    }
}
