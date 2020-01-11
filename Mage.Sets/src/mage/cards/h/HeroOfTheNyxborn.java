package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.HumanSoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeroOfTheNyxborn extends CardImpl {

    public HeroOfTheNyxborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{R}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Hero of the Nyxborn enters the battlefield, create a 1/1 white Human Soldier creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new HumanSoldierToken())));

        // Whenever you cast a spell that targets Hero of the Nyxborn, creatures you control get +1/+0 until end of turn.
        this.addAbility(new HeroicAbility(
                new BoostControlledEffect(1, 0, Duration.EndOfTurn), false, false
        ));
    }

    private HeroOfTheNyxborn(final HeroOfTheNyxborn card) {
        super(card);
    }

    @Override
    public HeroOfTheNyxborn copy() {
        return new HeroOfTheNyxborn(this);
    }
}
