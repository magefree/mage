package mage.cards.p;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterSource;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author cbt33, Plopman (Circle of Protection: Red)
 */
public final class PilgrimOfJustice extends CardImpl {

    private static final FilterSource filter = new FilterSource("red source");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public PilgrimOfJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Protection from red
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));
        // {W}, Sacrifice Pilgrim of Justice: The next time a red source of your choice would deal damage this turn, prevent that damage.
        Ability ability = new SimpleActivatedAbility(
                new PreventNextDamageFromChosenSourceEffect(Duration.EndOfTurn, false, filter),
                new ManaCostsImpl<>("{W}")
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private PilgrimOfJustice(final PilgrimOfJustice card) {
        super(card);
    }

    @Override
    public PilgrimOfJustice copy() {
        return new PilgrimOfJustice(this);
    }
}