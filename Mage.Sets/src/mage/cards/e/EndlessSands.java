package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnCreaturesFromExileEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetControlledCreaturePermanent;

public final class EndlessSands extends CardImpl {

    public EndlessSands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        
        this.subtype.add(SubType.DESERT);
                
        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        
        // {2}, {T}: Exile target creature you control.
        Ability exileAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(this.getId(), this.getIdName()), new ManaCostsImpl("{2}"));
        exileAbility.addCost(new TapSourceCost());
        exileAbility.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(exileAbility);
        
        // {4}, {T}, Sacrifice Endless Sands: Return each creature card exiled with Endless Sands to the battlefield under its owner's control.
        ReturnCreaturesFromExileEffect returnFromExileEffect = new ReturnCreaturesFromExileEffect(this.getId(), true, "Return each creature card exiled with {this} to the battlefield under its owner's control.");
        Ability returnAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, returnFromExileEffect,  new ManaCostsImpl("{4}"));
        returnAbility.addCost(new TapSourceCost());
        returnAbility.addCost(new SacrificeSourceCost());
        this.addAbility(returnAbility);
    }

    private EndlessSands(final EndlessSands card) {
        super(card);
    }

    @Override
    public EndlessSands copy() {
        return new EndlessSands(this);
    }
}
