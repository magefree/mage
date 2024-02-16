package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.SweepNumber;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.effects.keyword.SweepEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class SinkIntoTakenuma extends CardImpl {

    public SinkIntoTakenuma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");
        this.subtype.add(SubType.ARCANE);

        // Sweep - Return any number of Swamps you control to their owner's hand. Target player discards a card for each Swamp returned this way.
        this.getSpellAbility().addEffect(new SweepEffect(SubType.SWAMP));
        this.getSpellAbility().addEffect(new DiscardTargetEffect(SweepNumber.SWAMP));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private SinkIntoTakenuma(final SinkIntoTakenuma card) {
        super(card);
    }

    @Override
    public SinkIntoTakenuma copy() {
        return new SinkIntoTakenuma(this);
    }
}
