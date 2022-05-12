package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author North
 */
public final class InsatiableSouleater extends CardImpl {

    public InsatiableSouleater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(1);

        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{G/P}")));
    }

    private InsatiableSouleater(final InsatiableSouleater card) {
        super(card);
    }

    @Override
    public InsatiableSouleater copy() {
        return new InsatiableSouleater(this);
    }
}
