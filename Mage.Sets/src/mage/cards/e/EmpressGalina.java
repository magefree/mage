package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class EmpressGalina extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("legendary permanent");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public EmpressGalina(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK, SubType.NOBLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {U}{U}, {tap}: Gain control of target legendary permanent.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainControlTargetEffect(Duration.Custom), new ManaCostsImpl<>("{U}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private EmpressGalina(final EmpressGalina card) {
        super(card);
    }

    @Override
    public EmpressGalina copy() {
        return new EmpressGalina(this);
    }
}
