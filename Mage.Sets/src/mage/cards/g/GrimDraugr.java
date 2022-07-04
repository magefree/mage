package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class GrimDraugr extends CardImpl {

    public GrimDraugr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {1}{S}: Grim Draugr gets +1/+0 and gains menace until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn).setText("{this} gets +1/+0"),
                new ManaCostsImpl<>("{1}{S}")
        );
        ability.addEffect(new GainAbilitySourceEffect(
                new MenaceAbility(), Duration.EndOfTurn).setText("and gains menace until end of turn. " +
                "<i>(It can't be blocked except by two or more creatures. " +
                "{S} can be paid with one mana from a snow source.)</i>")
        );
        this.addAbility(ability);
    }

    private GrimDraugr(final GrimDraugr card) {
        super(card);
    }

    @Override
    public GrimDraugr copy() {
        return new GrimDraugr(this);
    }
}
