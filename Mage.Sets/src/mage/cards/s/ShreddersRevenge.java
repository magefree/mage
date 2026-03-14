package mage.cards.s;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author muz
 */
public final class ShreddersRevenge extends CardImpl {

    public ShreddersRevenge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Choose one --
        // * Target player discards two cards.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // * Target player draws two cards and loses 2 life.
        this.getSpellAbility().addMode(new Mode(new DrawCardTargetEffect(2)).addEffect(new LoseLifeTargetEffect(2).withTargetDescription("and")).addTarget(new TargetPlayer()));
    }

    private ShreddersRevenge(final ShreddersRevenge card) {
        super(card);
    }

    @Override
    public ShreddersRevenge copy() {
        return new ShreddersRevenge(this);
    }
}
