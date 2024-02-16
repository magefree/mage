package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ElfKnightToken;

/**
 *
 * @author TheElk801
 */
public final class ConclaveGuildmage extends CardImpl {

    public ConclaveGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {G}, {T}: Creatures you control gain trample until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityControlledEffect(
                        TrampleAbility.getInstance(),
                        Duration.EndOfTurn,
                        StaticFilters.FILTER_PERMANENT_CREATURES
                ), new ManaCostsImpl<>("{G}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {5}{W}, {T}: Create a 2/2 green and white Elf Knight creature token with vigilance.
        ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new ElfKnightToken()),
                new ManaCostsImpl<>("{5}{W}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ConclaveGuildmage(final ConclaveGuildmage card) {
        super(card);
    }

    @Override
    public ConclaveGuildmage copy() {
        return new ConclaveGuildmage(this);
    }
}
