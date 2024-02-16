
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class SmolderInitiate extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell("black spell");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public SmolderInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a player casts a black spell, you may pay {1}. If you do, target player loses 1 life.
        Ability ability = new SpellCastAllTriggeredAbility(new DoIfCostPaid(new LoseLifeTargetEffect(1), new ManaCostsImpl<>("{1}")), filter, false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        
    }

    private SmolderInitiate(final SmolderInitiate card) {
        super(card);
    }

    @Override
    public SmolderInitiate copy() {
        return new SmolderInitiate(this);
    }
}
