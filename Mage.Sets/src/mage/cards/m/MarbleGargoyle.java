package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MarbleGargoyle extends CardImpl {

    public MarbleGargoyle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {W}: Marble Gargoyle gets +0/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(0, 1, Duration.EndOfTurn), new ManaCostsImpl<>("{W}")
        ));
    }

    private MarbleGargoyle(final MarbleGargoyle card) {
        super(card);
    }

    @Override
    public MarbleGargoyle copy() {
        return new MarbleGargoyle(this);
    }
}
