
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class NivMizzetDracogenius extends CardImpl {

    public NivMizzetDracogenius(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.WIZARD);


        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever Niv-Mizzet, Dracogenius deals damage to a player, you may draw a card.
        this.addAbility(new DealsDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1), true));
        
        // {U}{R}: Niv-Mizzet, Dracogenius deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1),new ManaCostsImpl<>("{U}{R}"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private NivMizzetDracogenius(final NivMizzetDracogenius card) {
        super(card);
    }

    @Override
    public NivMizzetDracogenius copy() {
        return new NivMizzetDracogenius(this);
    }
}
