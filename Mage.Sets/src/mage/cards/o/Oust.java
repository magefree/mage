package mage.cards.o;

import mage.abilities.effects.common.GainLifeTargetControllerEffect;
import mage.abilities.effects.common.PutIntoLibraryNFromTopTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class Oust extends CardImpl {

    public Oust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}");

        // Put target creature into its owner's library second from the top. Its controller gains 3 life.
        this.getSpellAbility().addEffect(new PutIntoLibraryNFromTopTargetEffect(2));
        this.getSpellAbility().addEffect(new GainLifeTargetControllerEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Oust(final Oust card) {
        super(card);
    }

    @Override
    public Oust copy() {
        return new Oust(this);
    }
}
