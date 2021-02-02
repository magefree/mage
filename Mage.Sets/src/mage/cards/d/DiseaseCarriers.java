
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class DiseaseCarriers extends CardImpl {

    public DiseaseCarriers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.RAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Disease Carriers dies, target creature gets -2/-2 until end of turn.
        Ability ability = new DiesSourceTriggeredAbility(new BoostTargetEffect(-2, -2, Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DiseaseCarriers(final DiseaseCarriers card) {
        super(card);
    }

    @Override
    public DiseaseCarriers copy() {
        return new DiseaseCarriers(this);
    }
}
