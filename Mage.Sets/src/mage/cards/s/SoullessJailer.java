package mage.cards.s;

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
import mage.game.events.ZoneChangeEvent;

import java.util.UUID;

public class SoullessJailer extends CardImpl {
    public SoullessJailer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.addSubType(SubType.PHYREXIAN);
        this.addSubType(SubType.GOLEM);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        //Permanent cards in graveyards can’t enter the battlefield.
        this.addAbility(new SimpleStaticAbility(new SoullessJailerEnterEffect()));

        //Players can’t cast noncreature spells from graveyards or exile.
        this.addAbility(new SimpleStaticAbility(new SoullessJailerCastEffect()));
    }

    private SoullessJailer(final SoullessJailer card) {
        super(card);
    }

    @Override
    public SoullessJailer copy() {
        return new SoullessJailer(this);
    }
}

class SoullessJailerEnterEffect extends ContinuousRuleModifyingEffectImpl {

    SoullessJailerEnterEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "permanent cards in graveyards can't enter the battlefield";
    }

    private SoullessJailerEnterEffect(final SoullessJailerEnterEffect effect) {
        super(effect);
    }

    @Override
    public SoullessJailerEnterEffect copy() {
        return new SoullessJailerEnterEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() != Zone.BATTLEFIELD
                || zEvent.getFromZone() != Zone.GRAVEYARD) {
            return false;
        }
        Card card = game.getCard(zEvent.getTargetId());
        return card != null && card.isPermanent(game);
    }
}

class SoullessJailerCastEffect extends ContinuousRuleModifyingEffectImpl {

    SoullessJailerCastEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "players can't cast noncreature spells from graveyards or exile";
    }

    private SoullessJailerCastEffect(final SoullessJailerCastEffect effect) {
        super(effect);
    }

    @Override
    public SoullessJailerCastEffect copy() {
        return new SoullessJailerCastEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.getCard(event.getSourceId()) != null
                && !game.getCard(event.getSourceId()).isCreature(game)
                && (game.getState().getZone(event.getSourceId()) == Zone.GRAVEYARD
                || game.getState().getZone(event.getSourceId()) == Zone.EXILED);
    }
}
