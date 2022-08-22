package mage.cards.a;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CantBeCounteredControlledEffect;
import mage.abilities.effects.common.continuous.CreaturesBecomeOtherTypeEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;
import mage.filter.FilterSpell;

public class AllosaurusShepherd extends CardImpl {

    private static final FilterSpell greenSpellsFilter = new FilterSpell("Green spells you control");
    private static final FilterPermanent elvesFilter = new FilterControlledCreaturePermanent("each Elf creature you control");

    static {
        greenSpellsFilter.add(new ColorPredicate(ObjectColor.GREEN));
        elvesFilter.add(SubType.ELF.getPredicate());
    }

    public AllosaurusShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //Allosaurus Shepherd can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        //Green spells you control can't be countered.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new CantBeCounteredControlledEffect(greenSpellsFilter, null, Duration.WhileOnBattlefield)));

        //4GG: Until end of turn, each Elf creature you control has base power and toughness 5/5 
        // and becomes a Dinosaur in addition to its other creature types.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SetBasePowerToughnessAllEffect(5, 5, Duration.EndOfTurn, elvesFilter, true)
                        .setText("Until end of turn, each Elf creature you control has base power and toughness 5/5"),
                new ManaCostsImpl<>("{4}{G}{G}"));
        ability.addEffect(new CreaturesBecomeOtherTypeEffect(elvesFilter, SubType.DINOSAUR, Duration.EndOfTurn)
                .setText("and becomes a Dinosaur in addition to its other creature types"));
        this.addAbility(ability);

    }

    private AllosaurusShepherd(final AllosaurusShepherd card) {
        super(card);
    }

    @Override
    public AllosaurusShepherd copy() {
        return new AllosaurusShepherd(this);
    }

}
