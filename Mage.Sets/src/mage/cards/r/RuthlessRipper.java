
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author emerald000
 */
public final class RuthlessRipper extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("a black card in your hand");
    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public RuthlessRipper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        
        // Morph - Reveal a black card in your hand.
        this.addAbility(new MorphAbility(new RevealTargetFromHandCost(new TargetCardInHand(filter))));

        // When Ruthless Ripper is turned face up, target player loses 2 life.
        Effect effect = new LoseLifeTargetEffect(2);
        effect.setText("target player loses 2 life");
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(effect);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private RuthlessRipper(final RuthlessRipper card) {
        super(card);
    }

    @Override
    public RuthlessRipper copy() {
        return new RuthlessRipper(this);
    }
}
