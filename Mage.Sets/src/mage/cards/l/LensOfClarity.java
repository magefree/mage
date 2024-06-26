package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.LookAtOpponentFaceDownCreaturesAnyTimeEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class LensOfClarity extends CardImpl {

    public LensOfClarity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // You may look at the top card of your library and at face-down creatures you don't control.
        Ability ability = new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()
                .setText("you may look at the top card of your library"));
        ability.addEffect(new LookAtOpponentFaceDownCreaturesAnyTimeEffect()
                .setText("and at face-down creatures you don't control any time"));
        this.addAbility(ability);
    }

    private LensOfClarity(final LensOfClarity card) {
        super(card);
    }

    @Override
    public LensOfClarity copy() {
        return new LensOfClarity(this);
    }
}
