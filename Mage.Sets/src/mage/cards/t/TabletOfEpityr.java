package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.ZoneChangeAllTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;

import java.util.UUID;

/**
 * @author MarcoMarin
 */
public final class TabletOfEpityr extends CardImpl {


    public TabletOfEpityr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // Whenever an artifact you control is put into a graveyard from the battlefield, you may pay {1}. If you do, you gain 1 life.
        Effect effect = new DoIfCostPaid(new GainLifeEffect(1), new GenericManaCost(1));
        effect.setText("you may pay {1}. If you do, you gain 1 life.");
        Ability ability = new ZoneChangeAllTriggeredAbility(Zone.BATTLEFIELD, Zone.BATTLEFIELD, Zone.GRAVEYARD,
                effect, new FilterControlledArtifactPermanent(),
                "Whenever an artifact you control is put into a graveyard from the battlefield, ", false);
        this.addAbility(ability);
    }

    private TabletOfEpityr(final TabletOfEpityr card) {
        super(card);
    }

    @Override
    public TabletOfEpityr copy() {
        return new TabletOfEpityr(this);
    }
}
