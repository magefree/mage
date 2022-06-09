
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.costs.common.MillCardsCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */


public final class RotFarmSkeleton extends CardImpl {

    public RotFarmSkeleton (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{G}");
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.SKELETON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Rot Farm Skeleton can't block.
        this.addAbility(new CantBlockAbility());
        
        // 2{B}{G}, Put the top four cards of your library into your graveyard: Return Rot Farm Skeleton from your graveyard to the battlefield. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(false,false), new ManaCostsImpl<>("{2}{B}{G}"));
        ability.addCost(new MillCardsCost(4));
        this.addAbility(ability);

    }

    public RotFarmSkeleton (final RotFarmSkeleton card) {
        super(card);
    }

    @Override
    public RotFarmSkeleton copy() {
        return new RotFarmSkeleton(this);
    }

}
