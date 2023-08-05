
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class TsaboTavoc extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("legendary creatures");  
    private static final FilterCreaturePermanent filterDestroy = new FilterCreaturePermanent("legendary creature");
    
    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filterDestroy.add(SuperType.LEGENDARY.getPredicate());
    }
    
    public TsaboTavoc(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(7);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // protection from legendary creatures
        this.addAbility(new ProtectionAbility(filter));
        // {B}{B}, {tap}: Destroy target legendary creature. It can't be regenerated.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(true), new ManaCostsImpl<>("{B}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filterDestroy));
        this.addAbility(ability);        
    }

    private TsaboTavoc(final TsaboTavoc card) {
        super(card);
    }

    @Override
    public TsaboTavoc copy() {
        return new TsaboTavoc(this);
    }
}
