package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HermiticNautilus extends CardImpl {

    public HermiticNautilus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.NAUTILUS);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {1}{U}: Hermitic Nautilus gets +3/-3 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(3, -3, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{U}")
        ));
    }

    private HermiticNautilus(final HermiticNautilus card) {
        super(card);
    }

    @Override
    public HermiticNautilus copy() {
        return new HermiticNautilus(this);
    }
}
