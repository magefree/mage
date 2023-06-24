package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 *
 * @author cbt33, LevelX2 (Walk the Aeons), KholdFuzion (Dandan)
 */
public final class Dreamwinder extends CardImpl {
               
    public Dreamwinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.SERPENT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Dreamwinder can't attack unless defending player controls an Island.
        this.addAbility(new SimpleStaticAbility(new CantAttackUnlessDefenderControllsPermanent(new FilterLandPermanent(SubType.ISLAND,"an Island"))));
        // {U}, Sacrifice an Island: Target land becomes an Island until end of turn.
        Ability ability = new SimpleActivatedAbility(new BecomesBasicLandTargetEffect(Duration.EndOfTurn, SubType.ISLAND), new ColoredManaCost(ColoredManaSymbol.U));
        ability.addTarget(new TargetLandPermanent().withChooseHint("becomes an Island"));
        FilterControlledLandPermanent filter = new FilterControlledLandPermanent("an Island");
        filter.add(SubType.ISLAND.getPredicate());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, filter, true)));
        this.addAbility(ability);
    }   
    

    private Dreamwinder(final Dreamwinder card) {
        super(card);
    }

    @Override
    public Dreamwinder copy() {
        return new Dreamwinder(this);
    }
}
