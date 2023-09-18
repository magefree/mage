package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CabarettiInitiate extends CardImpl {

    public CabarettiInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.RACCOON);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {2}{R/W}: Cabaretti Initiate gains double strike until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                DoubleStrikeAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{2}{R/W}")));
    }

    private CabarettiInitiate(final CabarettiInitiate card) {
        super(card);
    }

    @Override
    public CabarettiInitiate copy() {
        return new CabarettiInitiate(this);
    }
}
