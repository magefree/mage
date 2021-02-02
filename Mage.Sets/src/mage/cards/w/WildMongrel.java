
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesColorSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCardInHand;

/**
 * @author magenoxx_at_gmail.com
 */
public final class WildMongrel extends CardImpl {

    public WildMongrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Discard a card: Wild Mongrel gets +1/+1 and becomes the color of your choice until end of turn.
        Effect effect = new BoostSourceEffect(1, 1, Duration.EndOfTurn);
        effect.setText("{this} gets +1/+1");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new DiscardTargetCost(new TargetCardInHand()));
        effect = new BecomesColorSourceEffect(Duration.EndOfTurn);
        effect.setText("and becomes the color of your choice until end of turn.");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private WildMongrel(final WildMongrel card) {
        super(card);
    }

    @Override
    public WildMongrel copy() {
        return new WildMongrel(this);
    }
}
