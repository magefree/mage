package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class SkyshipStalker extends CardImpl {

    public SkyshipStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.CAT, SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {R}: Skyship Stalker gains +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostSourceEffect(
                1, 0, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{R}")));

        // {R}: Skyship Stalker gains first strike until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{R}")));

        // {R}: Skyship Stalker gains haste until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{R}")));
    }

    private SkyshipStalker(final SkyshipStalker card) {
        super(card);
    }

    @Override
    public SkyshipStalker copy() {
        return new SkyshipStalker(this);
    }
}
