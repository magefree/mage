
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author Plopman
 */
public final class HibernationSliver extends CardImpl {

    public HibernationSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{B}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // All Slivers have "Pay 2 life: Return this permanent to its owner's hand."
        Effect effect = new ReturnToHandSourceEffect(true);
        effect.setText("Return this permanent to its owner's hand");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new PayLifeCost(2));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(ability, Duration.WhileOnBattlefield,
                        new FilterPermanent(SubType.SLIVER, "All Slivers"), "All Slivers have \"Pay 2 life: Return this permanent to its owner's hand.\"")));
    }

    private HibernationSliver(final HibernationSliver card) {
        super(card);
    }

    @Override
    public HibernationSliver copy() {
        return new HibernationSliver(this);
    }
}
