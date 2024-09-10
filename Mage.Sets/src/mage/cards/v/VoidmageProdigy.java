package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.Target;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class VoidmageProdigy extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.WIZARD, "a Wizard");

    public VoidmageProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {U}{U}, Sacrifice a Wizard: Counter target spell.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new ManaCostsImpl<>("{U}{U}"));
        ability.addCost(new SacrificeTargetCost(filter));
        Target target = new TargetSpell();
        ability.addTarget(target);
        this.addAbility(ability);

        // Morph {U}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl<>("{U}")));
    }

    private VoidmageProdigy(final VoidmageProdigy card) {
        super(card);
    }

    @Override
    public VoidmageProdigy copy() {
        return new VoidmageProdigy(this);
    }
}
