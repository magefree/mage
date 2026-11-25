package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;



public final class CarnageGladiator extends CardImpl {

    public CarnageGladiator (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{R}");
        this.subtype.add(SubType.SKELETON, SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever a creature blocks, that creature's controller loses 1 life.
        this.addAbility(new BlocksAllTriggeredAbility(
                new LoseLifeTargetEffect(1).withTargetDescription("that creature's controller"),
                false, StaticFilters.FILTER_PERMANENT_CREATURE, SetTargetPointer.PLAYER
        ));

        // {1}{B}{R}: Renegerate Carnage Gladiator.
        this.addAbility(new SimpleActivatedAbility(new RegenerateSourceEffect(),new ManaCostsImpl<>("{1}{B}{R}")));


    }

    private CarnageGladiator(final CarnageGladiator card) {
        super(card);
    }

    @Override
    public CarnageGladiator copy() {
        return new CarnageGladiator(this);
    }

}
