package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WojekInvestigator extends CardImpl {

    public WojekInvestigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // At the beginning of your upkeep, investigate once for each opponent who has more cards in hand than you.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new InvestigateEffect(WojekInvestigatorValue.instance)
                        .setText("investigate once for each opponent who has more cards in hand than you"),
                TargetController.YOU, false
        ).addHint(WojekInvestigatorValue.getHint()));
    }

    private WojekInvestigator(final WojekInvestigator card) {
        super(card);
    }

    @Override
    public WojekInvestigator copy() {
        return new WojekInvestigator(this);
    }
}

enum WojekInvestigatorValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Opponents with more cards in hand than you", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player == null) {
            return 0;
        }
        int size = player.getHand().size();
        return game
                .getOpponents(sourceAbility.getControllerId())
                .stream()
                .map(game::getPlayer)
                .map(Player::getHand)
                .mapToInt(Set::size)
                .map(x -> x > size ? 1 : 0)
                .sum();
    }

    @Override
    public WojekInvestigatorValue copy() {
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
