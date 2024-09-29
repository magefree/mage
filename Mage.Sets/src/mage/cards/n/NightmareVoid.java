
package mage.cards.n;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.keyword.DredgeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author jonubuu
 */
public final class NightmareVoid extends CardImpl {

    public NightmareVoid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}");


        // Target player reveals their hand. You choose a card from it. That player discards that card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect());
        // Dredge 2
        this.addAbility(new DredgeAbility(2));
    }

    private NightmareVoid(final NightmareVoid card) {
        super(card);
    }

    @Override
    public NightmareVoid copy() {
        return new NightmareVoid(this);
    }
}
