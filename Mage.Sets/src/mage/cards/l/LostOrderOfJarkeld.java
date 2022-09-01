package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseOpponentEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LostOrderOfJarkeld extends CardImpl {

    public LostOrderOfJarkeld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // As Lost Order of Jarkeld enters the battlefield, choose an opponent.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseOpponentEffect(Outcome.Detriment)));

        // Lost Order of Jarkeld's power and toughness are each equal to 1 plus the number of creatures the chosen player controls.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SetBasePowerToughnessSourceEffect(new AdditiveDynamicValue(
                        CreaturesControlledByChosenPlayer.instance, StaticValue.get(1)
                ), Duration.EndOfGame)
        ));
    }

    private LostOrderOfJarkeld(final LostOrderOfJarkeld card) {
        super(card);
    }

    @Override
    public LostOrderOfJarkeld copy() {
        return new LostOrderOfJarkeld(this);
    }
}

enum CreaturesControlledByChosenPlayer implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility != null) {
            UUID playerId = (UUID) game.getState().getValue(sourceAbility.getSourceId() + ChooseOpponentEffect.VALUE_KEY);
            Player chosenPlayer = game.getPlayer(playerId);
            if (chosenPlayer != null) {
                return game.getBattlefield().countAll(new FilterCreaturePermanent(), chosenPlayer.getId(), game);
            }
        }
        return 0;
    }

    @Override
    public CreaturesControlledByChosenPlayer copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "1 plus the number of creatures controlled by chosen player";
    }

    @Override
    public String toString() {
        return "1";
    }
}
