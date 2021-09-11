package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FangbladeEviscerator extends CardImpl {

    public FangbladeEviscerator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
        this.color.setRed(true);
        this.transformable = true;
        this.nightCard = true;

        // {1}{R}: Fangblade Eviscerator gets +1/+0 and gains first strike until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostSourceEffect(
                1, 0, Duration.EndOfTurn
        ).setText("{this} gets +1/+0"), new ManaCostsImpl<>("{1}{R}"));
        ability.addEffect(new GainAbilitySourceEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains first strike until end of turn"));
        this.addAbility(ability);

        // {4}{R}: Creatures you control get +2/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostControlledEffect(
                2, 0, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{4}{R}")));

        // Nightbound
        this.addAbility(NightboundAbility.getInstance());
    }

    private FangbladeEviscerator(final FangbladeEviscerator card) {
        super(card);
    }

    @Override
    public FangbladeEviscerator copy() {
        return new FangbladeEviscerator(this);
    }
}
