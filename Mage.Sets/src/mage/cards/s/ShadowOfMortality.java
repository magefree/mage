package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShadowOfMortality extends CardImpl {

    private static final Hint hint = new ValueHint("Current net life loss", ShadowOfMortalityValue.instance);

    public ShadowOfMortality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{13}{B}{B}");

        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // If your life total is less than your starting life total, this spell costs {X} less to cast, where X is the difference.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SpellCostReductionForEachSourceEffect(
                        1, ShadowOfMortalityValue.instance
                ).setText("if your life total is less than your starting life total, " +
                        "this spell costs {X} less to cast, where X is the difference")
        ).addHint(hint));
    }

    private ShadowOfMortality(final ShadowOfMortality card) {
        super(card);
    }

    @Override
    public ShadowOfMortality copy() {
        return new ShadowOfMortality(this);
    }
}

enum ShadowOfMortalityValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        return player != null ? Math.max(game.getStartingLife() - player.getLife(), 0) : 0;
    }

    @Override
    public ShadowOfMortalityValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
