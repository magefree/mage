
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.ShadowAbility;
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
public final class ThalakosDeceiver extends CardImpl {

    public ThalakosDeceiver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.THALAKOS);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());
        // Whenever Thalakos Deceiver attacks and isn't blocked, you may sacrifice it. If you do, gain control of target creature.
        Ability ability = new AttacksAndIsNotBlockedTriggeredAbility(new DoIfCostPaid(new GainControlTargetEffect(Duration.EndOfGame), new SacrificeSourceCost()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ThalakosDeceiver(final ThalakosDeceiver card) {
        super(card);
    }

    @Override
    public ThalakosDeceiver copy() {
        return new ThalakosDeceiver(this);
    }
}
