package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.abilities.keyword.SwampcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InjectorCrocodile extends CardImpl {

    public InjectorCrocodile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CROCODILE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Injector Crocodile dies, incubate 3.
        this.addAbility(new DiesSourceTriggeredAbility(new IncubateEffect(3)));

        // Swampcycling {2}
        this.addAbility(new SwampcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private InjectorCrocodile(final InjectorCrocodile card) {
        super(card);
    }

    @Override
    public InjectorCrocodile copy() {
        return new InjectorCrocodile(this);
    }
}
