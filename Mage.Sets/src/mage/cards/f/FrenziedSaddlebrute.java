package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FrenziedSaddlebrute extends CardImpl {

    public FrenziedSaddlebrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // All creatures can attack your opponents and planeswalkers your opponents control as though those creatures had haste.
        this.addAbility(new SimpleStaticAbility(new FrenziedSaddlebruteEffect()));
    }

    private FrenziedSaddlebrute(final FrenziedSaddlebrute card) {
        super(card);
    }

    @Override
    public FrenziedSaddlebrute copy() {
        return new FrenziedSaddlebrute(this);
    }
}

class FrenziedSaddlebruteEffect extends AsThoughEffectImpl {

    FrenziedSaddlebruteEffect() {
        super(AsThoughEffectType.ATTACK_AS_HASTE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "all creatures can attack your opponents and planeswalkers " +
                "your opponents control as though those creatures had haste";
    }

    private FrenziedSaddlebruteEffect(final FrenziedSaddlebruteEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public FrenziedSaddlebruteEffect copy() {
        return new FrenziedSaddlebruteEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return game.getOpponents(source.getControllerId()).contains(affectedControllerId);
    }
}
