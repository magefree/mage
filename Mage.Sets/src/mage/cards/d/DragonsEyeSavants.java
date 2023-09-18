
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LookAtTargetPlayerHandEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;

/**
 *
 * @author emerald000
 */
public final class DragonsEyeSavants extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("a blue card in your hand");
    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public DragonsEyeSavants(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(0);
        this.toughness = new MageInt(6);

        // Morph - Reveal a blue card in your hand.
        this.addAbility(new MorphAbility(new RevealTargetFromHandCost(new TargetCardInHand(filter))));
        
        // When Dragon's Eye Savants is turned face up, look at target opponent's hand.
        Effect effect = new LookAtTargetPlayerHandEffect();
        effect.setText("look at target opponent's hand");
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(effect);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private DragonsEyeSavants(final DragonsEyeSavants card) {
        super(card);
    }

    @Override
    public DragonsEyeSavants copy() {
        return new DragonsEyeSavants(this);
    }
}
