package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.SoldierTokenWithHaste;

/**
 *
 * @author Plopman
 */
public final class SunhomeGuildmage extends CardImpl {

    public SunhomeGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        //{1}{R}{W}: Creatures you control get +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}{W}")));

        //{2}{R}{W}: Create a 1/1 red and white Soldier creature token with haste.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SoldierTokenWithHaste()), new ManaCostsImpl<>("{2}{R}{W}")));
    }

    private SunhomeGuildmage(final SunhomeGuildmage card) {
        super(card);
    }

    @Override
    public SunhomeGuildmage copy() {
        return new SunhomeGuildmage(this);
    }
}
