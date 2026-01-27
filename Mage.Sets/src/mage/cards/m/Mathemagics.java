package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Mathemagics extends CardImpl {

    public Mathemagics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{U}{U}");

        // Target player draws 2^X cards.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(MathemagicsValue.instance));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private Mathemagics(final Mathemagics card) {
        super(card);
    }

    @Override
    public Mathemagics copy() {
        return new Mathemagics(this);
    }
}

enum MathemagicsValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return CardUtil.overflowExp(2, GetXValue.instance.calculate(game, sourceAbility, effect));
    }

    @Override
    public MathemagicsValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "2<sup>X</sup>";
    }
}
