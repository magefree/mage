package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX
 */
public final class VillainousOgre extends CardImpl {

    private static final String rule = "As long as you control a Demon, {this} has \"{B}: Regenerate {this}.\"";
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Demon");
    static {
        filter.add(SubType.DEMON.getPredicate());
    }

    public VillainousOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.addAbility(new CantBlockAbility());

        // As long as you control a Demon, Villainous Ogre has "{B}: Regenerate Villainous Ogre."
        Ability regenAbility = new SimpleActivatedAbility(new RegenerateSourceEffect(), new ManaCostsImpl<>("{B}"));
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(regenAbility), new PermanentsOnTheBattlefieldCondition(filter), rule)));
    }

    private VillainousOgre(final VillainousOgre card) {
        super(card);
    }

    @Override
    public VillainousOgre copy() {
        return new VillainousOgre(this);
    }    

}
