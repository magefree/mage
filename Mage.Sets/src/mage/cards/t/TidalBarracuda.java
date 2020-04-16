package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TidalBarracuda extends CardImpl {

    private static final FilterCard filter = new FilterCard("spells");

    public TidalBarracuda(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.FISH);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Any player may cast spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(new CastAsThoughItHadFlashAllEffect(
                Duration.WhileOnBattlefield, filter, true
        )));

        // Your opponents can't cast spells during your turn.
        this.addAbility(new SimpleStaticAbility(new TidalBarracudaEffect()));
    }

    private TidalBarracuda(final TidalBarracuda card) {
        super(card);
    }

    @Override
    public TidalBarracuda copy() {
        return new TidalBarracuda(this);
    }
}

class TidalBarracudaEffect extends ContinuousRuleModifyingEffectImpl {

    TidalBarracudaEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Your opponents can't cast spells during your turn";
    }

    private TidalBarracudaEffect(final TidalBarracudaEffect effect) {
        super(effect);
    }

    @Override
    public TidalBarracudaEffect copy() {
        return new TidalBarracudaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.isActivePlayer(source.getControllerId()) &&
                game.getPlayer(source.getControllerId()).hasOpponent(event.getPlayerId(), game);
    }
}
