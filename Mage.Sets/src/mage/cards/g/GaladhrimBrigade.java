package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.SquadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GaladhrimBrigade extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.ELF, "Elves");

    public GaladhrimBrigade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Squad {1}{G}
        this.addAbility(new SquadAbility(new ManaCostsImpl<>("{1}{G}")));

        // Other Elves you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));
    }

    private GaladhrimBrigade(final GaladhrimBrigade card) {
        super(card);
    }

    @Override
    public GaladhrimBrigade copy() {
        return new GaladhrimBrigade(this);
    }
}
