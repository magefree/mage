package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.watchers.common.AmountOfDamageAPlayerReceivedThisTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GnollWarBand extends CardImpl {

    public GnollWarBand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.GNOLL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // This spell costs {1} less to cast for each opponent who was dealt damage this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(GnollWarBandValue.instance)
                .setCanWorksOnStackOnly(true)
                .setText("this spell costs {1} less to cast for each opponent who was dealt damage this turn")
        ), new AmountOfDamageAPlayerReceivedThisTurnWatcher());

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Myriad
        this.addAbility(new MyriadAbility());
    }

    private GnollWarBand(final GnollWarBand card) {
        super(card);
    }

    @Override
    public GnollWarBand copy() {
        return new GnollWarBand(this);
    }
}

enum GnollWarBandValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getOpponents(sourceAbility.getControllerId())
                .stream()
                .map(game
                        .getState()
                        .getWatcher(AmountOfDamageAPlayerReceivedThisTurnWatcher.class)
                        ::getAmountOfDamageReceivedThisTurn)
                .mapToInt(x -> Math.min(x, 1))
                .sum();
    }

    @Override
    public GnollWarBandValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
