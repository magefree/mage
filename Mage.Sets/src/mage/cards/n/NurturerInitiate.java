
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class NurturerInitiate extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell("a green spell");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public NurturerInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a player casts a green spell, you may pay {1}. If you do, target creature gets +1/+1 until end of turn.
        Ability ability = new SpellCastAllTriggeredAbility(new DoIfCostPaid(new BoostTargetEffect(1, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{1}")), filter, false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private NurturerInitiate(final NurturerInitiate card) {
        super(card);
    }

    @Override
    public NurturerInitiate copy() {
        return new NurturerInitiate(this);
    }
}
