package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

import java.util.UUID;

public final class MastersCouncillors extends CardImpl {

    private static final DynamicValue xValue = new GraveyardsWithXOrMoreCardsInIt(7);
    private static final Hint hint = new ValueHint(xValue.getMessage(), xValue);

    public MastersCouncillors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // This creature gets +2/+0 for each graveyard with seven or more cards in it.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                new MultipliedValue(xValue, 2), StaticValue.get(0), Duration.WhileOnBattlefield
        ).setText("{this} gets +2/+0 for each graveyard with seven or more cards in it.")).addHint(hint));

        // Whenever you draw your second card each turn, target player mills three cards.
        Ability ability = new DrawNthCardTriggeredAbility(new MillCardsTargetEffect(3), false, 2);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);


    }

    private MastersCouncillors(final MastersCouncillors card) {
        super(card);
    }

    @Override
    public MastersCouncillors copy() {
        return new MastersCouncillors(this);
    }
}

class GraveyardsWithXOrMoreCardsInIt implements DynamicValue {

    private final int xValue;

    public GraveyardsWithXOrMoreCardsInIt(int amount) {
        this.xValue = amount;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {

        int count = 0;
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player == null || player.getGraveyard().size() < xValue) {
                    continue;
                }
                count++;
            }
        }
        return count;
    }

    @Override
    public GraveyardsWithXOrMoreCardsInIt copy() {
        return new GraveyardsWithXOrMoreCardsInIt(xValue);
    }

    @Override
    public String getMessage() {
        return "Graveyards with " + CardUtil.numberToText(xValue) + " or more cards in it";
    }
}