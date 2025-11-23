package mage.cards.c;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledLandPermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrackedEarthTechnique extends CardImpl {

    public CrackedEarthTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}");

        this.subtype.add(SubType.LESSON);

        // Earthbend 3, then earthbend 3. You gain 3 life.
        this.getSpellAbility().addEffect(new EarthbendTargetEffect(3, false));
        this.getSpellAbility().addEffect(new EarthbendTargetEffect(3, false)
                .concatBy(", then")
                .setTargetPointer(new SecondTargetPointer())
        );
        this.getSpellAbility().addTarget(new TargetControlledLandPermanent().withChooseHint("first target"));
        this.getSpellAbility().addTarget(new TargetControlledLandPermanent().withChooseHint("second target"));
        this.getSpellAbility().addEffect(new GainLifeEffect(3));
    }

    private CrackedEarthTechnique(final CrackedEarthTechnique card) {
        super(card);
    }

    @Override
    public CrackedEarthTechnique copy() {
        return new CrackedEarthTechnique(this);
    }
}
