package mage.cards.a;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawSecondCardTriggeredAbility;
import mage.abilities.common.DrawVariableCardTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseOpponentEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.DrakeToken;
import mage.players.Player;

/**
 *
 * @author AustinYQM
 */
public final class AlandraSkyDreamer extends CardImpl {

    public AlandraSkyDreamer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever you draw your second card each turn, create a 2/2 blue Drake creature token with flying.
        this.addAbility(new DrawSecondCardTriggeredAbility(new CreateTokenEffect(new DrakeToken()), false));
        // Whenever you draw your fifth card each turn, Alandra, Sky Dreamer and Drakes you control each get +X/+X until end of turn, where X is the number of cards in your hand.
        Ability ability = new DrawVariableCardTriggeredAbility(new BoostSourceEffect(AlandraSkyDreamerValue.instance, AlandraSkyDreamerValue.instance, Duration.EndOfTurn), 5, false);
        ability.addEffect(new BoostAllEffect(AlandraSkyDreamerValue.instance, AlandraSkyDreamerValue.instance, Duration.EndOfTurn, new FilterCreaturePermanent(SubType.DRAKE, "Drake"), true));
        this.addAbility(ability);

    }

    private AlandraSkyDreamer(final AlandraSkyDreamer card) {
        super(card);
    }

    @Override
    public AlandraSkyDreamer copy() {
        return new AlandraSkyDreamer(this);
    }
}
enum AlandraSkyDreamerValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return Optional.ofNullable(game.getPlayer(
                        (UUID) game.getState().getValue(sourceAbility.getSourceId() + ChooseOpponentEffect.VALUE_KEY)
                ))
                .filter(Objects::nonNull)
                .map(Player::getHand)
                .map(Set::size)
                .orElse(0);
    }

    @Override
    public mage.cards.a.AlandraSkyDreamerValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}
