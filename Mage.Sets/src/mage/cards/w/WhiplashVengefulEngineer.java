package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.common.EquippedSourceCondition;
import mage.abilities.dynamicvalue.common.EquipmentAttachedCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class WhiplashVengefulEngineer extends CardImpl {

    public WhiplashVengefulEngineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whiplash enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Whenever Whiplash attacks, if he's equipped, each opponent loses X life and you gain X life,
        // where X is the number of Equipment attached to him.
        this.addAbility(new AttacksTriggeredAbility(new WhiplashVengefulEngineerEffect())
            .withInterveningIf(EquippedSourceCondition.instance));
    }

    private WhiplashVengefulEngineer(final WhiplashVengefulEngineer card) {
        super(card);
    }

    @Override
    public WhiplashVengefulEngineer copy() {
        return new WhiplashVengefulEngineer(this);
    }
}

class WhiplashVengefulEngineerEffect extends OneShotEffect {

    WhiplashVengefulEngineerEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent loses X life and you gain X life, " +
                "where X is the number of Equipment attached to him";
    }

    private WhiplashVengefulEngineerEffect(final WhiplashVengefulEngineerEffect effect) {
        super(effect);
    }

    @Override
    public WhiplashVengefulEngineerEffect copy() {
        return new WhiplashVengefulEngineerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = new EquipmentAttachedCount().calculate(game, source, this);
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                opponent.loseLife(amount, game, source, false);
            }
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.gainLife(amount, game, source);
        }
        return true;
    }
}
