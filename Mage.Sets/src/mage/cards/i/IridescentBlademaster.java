package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IridescentBlademaster extends CardImpl {

    public IridescentBlademaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {3}{G}: Iridescent Blademaster gets +2/+2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn), new ManaCostsImpl<>("{3}{G}")));
    }

    private IridescentBlademaster(final IridescentBlademaster card) {
        super(card);
    }

    @Override
    public IridescentBlademaster copy() {
        return new IridescentBlademaster(this);
    }
}
