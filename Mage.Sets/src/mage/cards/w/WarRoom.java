package mage.cards.w;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CommanderCardType;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WarRoom extends CardImpl {

    public WarRoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {3}, {T}, Pay life equal to the number of colors in your commanders' color identity: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new PayLifeCost(
                WarRoomValue.instance, "life equal to the number of colors in your commanders' color identity"
        ));
        this.addAbility(ability);
    }

    private WarRoom(final WarRoom card) {
        super(card);
    }

    @Override
    public WarRoom copy() {
        return new WarRoom(this);
    }
}

enum WarRoomValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller == null) {
            return 0;
        }
        ObjectColor color = new ObjectColor();
        // if no commander then cost can't be paid
        boolean hasCommander = false;
        for (UUID commanderId : game.getCommandersIds(controller, CommanderCardType.COMMANDER_OR_OATHBREAKER, false)) {
            Card commander = game.getCard(commanderId);
            if (commander == null) {
                continue;
            }
            FilterMana commanderMana = commander.getColorIdentity();
            if (commanderMana.isWhite()) {
                color.setWhite(true);
            }
            if (commanderMana.isBlue()) {
                color.setBlue(true);
            }
            if (commanderMana.isBlack()) {
                color.setBlack(true);
            }
            if (commanderMana.isRed()) {
                color.setRed(true);
            }
            if (commanderMana.isGreen()) {
                color.setGreen(true);
            }
            hasCommander = true;
        }
        return hasCommander ? color.getColorCount() : Integer.MAX_VALUE;
    }

    @Override
    public WarRoomValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}