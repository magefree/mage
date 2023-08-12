package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.token.VampireKnightToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class ElendaAndAzor extends CardImpl {

    public ElendaAndAzor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE, SubType.KNIGHT, SubType.SPHINX);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // Whenever Elenda and Azor attacks, you may pay {X}{W}{U}{B}. If you do, draw X cards.
        this.addAbility(new AttacksTriggeredAbility(new ElendaAndAzorEffect(), false));

        // At the beginning of each end step, you may pay 4 life. If you do, create a number of 1/1 black Vampire
        // Knight creature tokens with lifelink equal to the number of cards you've drawn this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DoIfCostPaid(new CreateTokenEffect(
                new VampireKnightToken(), CardsDrawnThisTurnDynamicValue.instance)
                .setText("create a number of 1/1 black Vampire Knight creature tokens with lifelink " +
                        "equal to the number of cards you've drawn this turn"), new PayLifeCost(4)),
                TargetController.ANY, false).addHint(CardsDrawnThisTurnDynamicValue.getHint()));
    }

    private ElendaAndAzor(final ElendaAndAzor card) {
        super(card);
    }

    @Override
    public ElendaAndAzor copy() {
        return new ElendaAndAzor(this);
    }
}

class ElendaAndAzorEffect extends OneShotEffect {

    ElendaAndAzorEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay {X}{W}{U}{B}. If you do, draw X cards";
    }

    ElendaAndAzorEffect(final ElendaAndAzorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ManaCosts cost = new ManaCostsImpl<>("{X}{W}{U}{B}");
            if (controller.chooseUse(Outcome.Damage, "Pay " + cost.getText() + "? If you do, draw X cards.", source, game)) {
                int costX = controller.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
                cost.add(new GenericManaCost(costX));
                if (cost.pay(source, game, source, source.getControllerId(), false, null)) {
                    controller.resetStoredBookmark(game); // otherwise you can undo the payment
                    controller.drawCards(costX, source, game);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ElendaAndAzorEffect copy() {
        return new ElendaAndAzorEffect(this);
    }
}
