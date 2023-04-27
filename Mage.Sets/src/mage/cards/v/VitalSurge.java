package mage.cards.v;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class VitalSurge extends CardImpl {

    public VitalSurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");
        this.subtype.add(SubType.ARCANE);


        // You gain 3 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(3));
        // Splice onto Arcane {1}{G}
        this.addAbility(new SpliceAbility(SpliceAbility.ARCANE, "{1}{G}"));
    }

    private VitalSurge(final VitalSurge card) {
        super(card);
    }

    @Override
    public VitalSurge copy() {
        return new VitalSurge(this);
    }
}
