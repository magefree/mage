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
public final class PilgrimOfVirtue extends CardImpl {

    private static final FilterSource filter = new FilterSource("black source");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public PilgrimOfVirtue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Protection from black
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));
        // {W}, Sacrifice Pilgrim of Virtue: The next time a black source of your choice would deal damage this turn, prevent that damage.
        Ability ability = new SimpleActivatedAbility(
                new PreventNextDamageFromChosenSourceEffect(Duration.EndOfTurn, false, filter),
                new ManaCostsImpl<>("{W}")
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private PilgrimOfVirtue(final PilgrimOfVirtue card) {
        super(card);
    }

    @Override
    public PilgrimOfVirtue copy() {
        return new PilgrimOfVirtue(this);
    }
}