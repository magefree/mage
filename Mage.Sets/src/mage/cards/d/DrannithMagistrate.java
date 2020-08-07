package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DrannithMagistrate extends CardImpl {

    public DrannithMagistrate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Your opponents can't cast spells from anywhere other than their hands.
        this.addAbility(new SimpleStaticAbility(new DrannithMagistrateEffect()));
    }

    private DrannithMagistrate(final DrannithMagistrate card) {
        super(card);
    }

    @Override
    public DrannithMagistrate copy() {
        return new DrannithMagistrate(this);
    }
}

class DrannithMagistrateEffect extends ContinuousRuleModifyingEffectImpl {

    DrannithMagistrateEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Your opponents can't cast spells from anywhere other than their hands";
    }

    private DrannithMagistrateEffect(final DrannithMagistrateEffect effect) {
        super(effect);
    }

    @Override
    public DrannithMagistrateEffect copy() {
        return new DrannithMagistrateEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            return false;
        }
        Card card = game.getCard(event.getSourceId());
        if (card == null) {
            return false;
        }
        return game.getState().getZone(card.getId()) != Zone.HAND;
    }
}
