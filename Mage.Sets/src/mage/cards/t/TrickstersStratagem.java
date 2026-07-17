package mage.cards.t;

import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.abilities.effects.keyword.ConniveTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class TrickstersStratagem extends CardImpl {

    public TrickstersStratagem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // The owner of target creature an opponent controls puts it into their library second from the top or on the bottom.
        // Then up to one target creature you control connives.
        this.getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect(2, true));
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
        this.getSpellAbility().addEffect(new ConniveTargetEffect()
                .setTargetPointer(new SecondTargetPointer())
                .concatBy("Then"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, 1));
    }

    private TrickstersStratagem(final TrickstersStratagem card) {
        super(card);
    }

    @Override
    public TrickstersStratagem copy() {
        return new TrickstersStratagem(this);
    }
}
