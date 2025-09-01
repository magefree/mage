package mage.cards.a;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.BoastAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class ArniBrokenbrow extends CardImpl {

    public ArniBrokenbrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Boast — {1}: You may change Arni Brokenbrow's base power to 1 plus the greatest power among other creatures you control until end of turn.
        this.addAbility(new BoastAbility(new ArniBrokenbrowEffect(), new GenericManaCost(1))
                .addHint(GreatestAmongPermanentsValue.POWER_OTHER_CONTROLLED_CREATURES.getHint()));
    }

    private ArniBrokenbrow(final ArniBrokenbrow card) {
        super(card);
    }

    @Override
    public ArniBrokenbrow copy() {
        return new ArniBrokenbrow(this);
    }
}

class ArniBrokenbrowEffect extends OneShotEffect {

    ArniBrokenbrowEffect() {
        super(Outcome.BoostCreature);
        staticText = "you may change {this}'s base power to 1 plus the greatest power among other creatures you control until end of turn";
    }

    private ArniBrokenbrowEffect(final ArniBrokenbrowEffect effect) {
        super(effect);
    }

    @Override
    public ArniBrokenbrowEffect copy() {
        return new ArniBrokenbrowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source);
        if (controller == null || mageObject == null) {
            return false;
        }
        int power = GreatestAmongPermanentsValue.POWER_OTHER_CONTROLLED_CREATURES.calculate(game, source, this);
        power += 1;
        if (controller.chooseUse(outcome, "Change base power of " + mageObject.getLogName() + " to "
                + power + " until end of turn?", source, game
        )) {
            game.addEffect(new SetBasePowerSourceEffect(power, Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }
}
