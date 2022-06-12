
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author djbrez
 */
public final class PitSpawn extends CardImpl {

    public PitSpawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}{B}");
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        
        // At the beginning of your upkeep, sacrifice Pit Spawn unless you pay {B}{B}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl<>("{B}{B}")), TargetController.YOU, false));

        // Whenever Pit Spawn deals damage to a creature, exile that creature.
        this.addAbility(new DealsDamageToACreatureTriggeredAbility(new ExileTargetEffect("exile that creature"), false, false, true));
    }   

    private PitSpawn(final PitSpawn card) {
        super(card);
    }

    @Override
    public PitSpawn copy() {
        return new PitSpawn(this);
    }
}
