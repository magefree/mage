package mage.cards.c;

import mage.abilities.effects.common.PutIntoLibraryNFromTopTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Chronostutter extends CardImpl {

    public Chronostutter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{5}{U}");

        // Put target creature into its owner's library second from the top.
        this.getSpellAbility().addEffect(new PutIntoLibraryNFromTopTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Chronostutter(final Chronostutter card) {
        super(card);
    }

    @Override
    public Chronostutter copy() {
        return new Chronostutter(this);
    }
}
