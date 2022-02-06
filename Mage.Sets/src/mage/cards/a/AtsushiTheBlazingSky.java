package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AtsushiTheBlazingSky extends CardImpl {

    public AtsushiTheBlazingSky(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Atsushi, the Blazing Sky dies, choose one —
        // • Exile the top two cards of your library. Until the end of your next turn, you may play those cards.
        Ability ability = new DiesSourceTriggeredAbility(new ExileTopXMayPlayUntilEndOfTurnEffect(
                3, false, Duration.UntilEndOfYourNextTurn
        ), false);

        // • Create three Treasure tokens.
        ability.addMode(new Mode(new CreateTokenEffect(new TreasureToken(), 3)));
        this.addAbility(ability);
    }

    private AtsushiTheBlazingSky(final AtsushiTheBlazingSky card) {
        super(card);
    }

    @Override
    public AtsushiTheBlazingSky copy() {
        return new AtsushiTheBlazingSky(this);
    }
}
