
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Styxo
 */
public final class SalvageSquad extends CardImpl {

    public SalvageSquad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}");
        this.subtype.add(SubType.JAWA);
        this.subtype.add(SubType.ARTIFICIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When you Salvage Squad enters the battlefied, you may sacrifice an artifact. If you do, you gain 2 life and draw two cards.
        DoIfCostPaid effect = new DoIfCostPaid(new GainLifeEffect(2), new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledArtifactPermanent())));
        effect.addEffect(new DrawCardSourceControllerEffect(2));
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect));
    }

    private SalvageSquad(final SalvageSquad card) {
        super(card);
    }

    @Override
    public SalvageSquad copy() {
        return new SalvageSquad(this);
    }
}
