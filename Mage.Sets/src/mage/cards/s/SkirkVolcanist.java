
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author fireshoes
 */
public final class SkirkVolcanist extends CardImpl {
    
    private static final FilterControlledLandPermanent filterSacrifice = new FilterControlledLandPermanent("two Mountains");

    static {
        filterSacrifice.add(SubType.MOUNTAIN.getPredicate());
    }

    public SkirkVolcanist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Morph-Sacrifice two Mountains.
        this.addAbility(new MorphAbility(this, new SacrificeTargetCost(new TargetControlledPermanent(2,2, filterSacrifice, false))));
        
        // When Skirk Volcanist is turned face up, it deals 3 damage divided as you choose among one, two, or three target creatures.
        Effect effect = new DamageMultiEffect(3);
        effect.setText("it deals 3 damage divided as you choose among one, two, or three target creatures");
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(effect);
        ability.addTarget(new TargetCreaturePermanentAmount(3));
        this.addAbility(ability);
    }

    private SkirkVolcanist(final SkirkVolcanist card) {
        super(card);
    }

    @Override
    public SkirkVolcanist copy() {
        return new SkirkVolcanist(this);
    }
}
