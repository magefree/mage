package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;

/**
 *
 * @author muz
 */
public final class OmnathLocusOfTheVoid extends CardImpl {

    private static final DynamicValue xValue = new TotalUnspentManaCount();
    private static final Hint hint = new ValueHint("Unspent mana", xValue);

    public OmnathLocusOfTheVoid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Omnath gets +1/+1 for each unspent mana you have.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(xValue, xValue, Duration.WhileOnBattlefield)).addHint(hint));

        // If you would lose unspent mana, that mana becomes colorless instead.
        this.addAbility(new SimpleStaticAbility(new OmnathUnspentManaBecomesColorlessEffect()));

        // Landfall -- Whenever a land you control enters, add {C}{C}.
        this.addAbility(new LandfallAbility(new BasicManaEffect(Mana.ColorlessMana(2))));
    }

    private OmnathLocusOfTheVoid(final OmnathLocusOfTheVoid card) {
        super(card);
    }

    @Override
    public OmnathLocusOfTheVoid copy() {
        return new OmnathLocusOfTheVoid(this);
    }
}

class TotalUnspentManaCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, mage.abilities.effects.Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player == null) {
            return 0;
        }
        return player.getManaPool().getMana().count();
    }

    @Override
    public DynamicValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "unspent mana you have";
    }

    @Override
    public String toString() {
        return "1";
    }
}

class OmnathUnspentManaBecomesColorlessEffect extends ContinuousEffectImpl {

    OmnathUnspentManaBecomesColorlessEffect() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "if you would lose unspent mana, that mana becomes colorless instead";
    }

    private OmnathUnspentManaBecomesColorlessEffect(final OmnathUnspentManaBecomesColorlessEffect effect) {
        super(effect);
    }

    @Override
    public OmnathUnspentManaBecomesColorlessEffect copy() {
        return new OmnathUnspentManaBecomesColorlessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.getManaPool().setManaBecomesColorless(true);
        }
        return true;
    }
}
