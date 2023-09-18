
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author emerald000
 */
public final class WatcherOfTheRoost extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("a white card in your hand");
    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public WatcherOfTheRoost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Morph - Reveal a white card in your hand
        this.addAbility(new MorphAbility(new RevealTargetFromHandCost(new TargetCardInHand(filter))));
        
        // When Watcher of the Roost is turned face up, you gain 2 life.
        Effect effect = new GainLifeEffect(2);
        effect.setText("you gain 2 life");
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(effect));
    }

    private WatcherOfTheRoost(final WatcherOfTheRoost card) {
        super(card);
    }

    @Override
    public WatcherOfTheRoost copy() {
        return new WatcherOfTheRoost(this);
    }
}
