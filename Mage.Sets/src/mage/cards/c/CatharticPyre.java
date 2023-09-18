package mage.cards.c;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.discard.DiscardAndDrawThatManyEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CatharticPyre extends CardImpl {

    public CatharticPyre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Choose one —
        // • Cathartic Pyre deals 3 damage to target creature or planeswalker.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());

        // • Discard up to two cards, then draw that many cards.
        this.getSpellAbility().addMode(new Mode(new DiscardAndDrawThatManyEffect(2)));
    }

    private CatharticPyre(final CatharticPyre card) {
        super(card);
    }

    @Override
    public CatharticPyre copy() {
        return new CatharticPyre(this);
    }
}
