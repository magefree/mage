
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class ThingFromTheDeep extends CardImpl {
    
    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("an Island");
    
    static{
        filter.add(SubType.ISLAND.getPredicate());
    }

    public ThingFromTheDeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{U}{U}{U}");
        this.subtype.add(SubType.LEVIATHAN);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // Whenever Thing from the Deep attacks, sacrifice it unless you sacrifice an Island.
        Effect effect = new SacrificeSourceUnlessPaysEffect(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        effect.setText("sacrifice it unless you sacrifice an Island");
        this.addAbility(new AttacksTriggeredAbility(effect, false));
    }

    private ThingFromTheDeep(final ThingFromTheDeep card) {
        super(card);
    }

    @Override
    public ThingFromTheDeep copy() {
        return new ThingFromTheDeep(this);
    }
}
