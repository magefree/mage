package mage.cards.c;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class ChameleonColossus extends CardImpl {

    private static final SourcePermanentPowerCount xValue = new SourcePermanentPowerCount();

    public ChameleonColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Changeling (This card is every creature type at all times.)
        this.addAbility(new ChangelingAbility());

        // Protection from black
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));

        // {2}{G}{G}: Chameleon Colossus gets +X/+X until end of turn, where X is its power.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(xValue, xValue, Duration.EndOfTurn, true)
                        .setText("{this} gets +X/+X until end of turn, where X is its power"),
                new ManaCostsImpl<>("{2}{G}{G}")
        ));
    }

    private ChameleonColossus(final ChameleonColossus card) {
        super(card);
    }

    @Override
    public ChameleonColossus copy() {
        return new ChameleonColossus(this);
    }
}
