package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class FlameChainMauler extends CardImpl {

    public FlameChainMauler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{R}: This creature gets +1/+0 and gains menace until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn).setText("{this} gets +1/+0"), 
                new ManaCostsImpl<>("{1}{R}")
        );
        ability.addEffect(new GainAbilitySourceEffect(new MenaceAbility()).setText("and gains menace until end of turn"));
        this.addAbility(ability);
    }

    private FlameChainMauler(final FlameChainMauler card) {
        super(card);
    }

    @Override
    public FlameChainMauler copy() {
        return new FlameChainMauler(this);
    }
}
