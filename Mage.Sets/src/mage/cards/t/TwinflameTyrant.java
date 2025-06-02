package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TwinflameTyrant extends CardImpl {

    public TwinflameTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If a source you control would deal damage to an opponent or a permanent an opponent controls, it deals double that damage instead.
        this.addAbility(new SimpleStaticAbility(new TwinflameTyrantEffect()));
    }

    private TwinflameTyrant(final TwinflameTyrant card) {
        super(card);
    }

    @Override
    public TwinflameTyrant copy() {
        return new TwinflameTyrant(this);
    }
}

class TwinflameTyrantEffect extends ReplacementEffectImpl {

    TwinflameTyrantEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "if a source you control would deal damage to an opponent " +
                "or a permanent an opponent controls, it deals double that damage instead";
    }

    private TwinflameTyrantEffect(final TwinflameTyrantEffect effect) {
        super(effect);
    }

    @Override
    public TwinflameTyrantEffect copy() {
        return new TwinflameTyrantEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PLAYER:
            case DAMAGE_PERMANENT:
                return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!source.isControlledBy(game.getControllerId(event.getSourceId()))) {
            return false;
        }
        switch (event.getType()) {
            case DAMAGE_PLAYER:
                return game
                        .getOpponents(source.getControllerId())
                        .contains(event.getTargetId());
            case DAMAGE_PERMANENT:
                return game
                        .getOpponents(source.getControllerId())
                        .contains(game.getControllerId(event.getTargetId()));
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }
}
