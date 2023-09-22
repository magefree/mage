package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class PlatinumAngel extends CardImpl {

    public PlatinumAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new SimpleStaticAbility(new PlatinumAngelEffect()));
    }

    private PlatinumAngel(final PlatinumAngel card) {
        super(card);
    }

    @Override
    public PlatinumAngel copy() {
        return new PlatinumAngel(this);
    }
}

class PlatinumAngelEffect extends ContinuousRuleModifyingEffectImpl {

    public PlatinumAngelEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, false, false);
        staticText = "You can't lose the game and your opponents can't win the game";
    }

    private PlatinumAngelEffect(final PlatinumAngelEffect effect) {
        super(effect);
    }

    @Override
    public PlatinumAngelEffect copy() {
        return new PlatinumAngelEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.WINS
                || event.getType() == GameEvent.EventType.LOSES;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        switch (event.getType()) {
            case WINS:
                return game.getOpponents(source.getControllerId()).contains(event.getPlayerId());
            case LOSES:
                return source.isControlledBy(event.getPlayerId());
        }
        return false;
    }
}
