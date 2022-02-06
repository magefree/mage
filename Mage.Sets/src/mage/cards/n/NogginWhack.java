package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.keyword.ProwlAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.TargetPlayer;

import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class NogginWhack extends CardImpl {

    public NogginWhack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.SORCERY}, "{2}{B}{B}");
        this.subtype.add(SubType.ROGUE);

        // Prowl {1}{B}
        this.addAbility(new ProwlAbility(this, "{1}{B}"));
        // Target player reveals three cards from their hand. You choose two of them. That player discards those cards.
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(2, TargetController.ANY, 3));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private NogginWhack(final NogginWhack card) {
        super(card);
    }

    @Override
    public NogginWhack copy() {
        return new NogginWhack(this);
    }
}
