package mage.cards.d;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.TargetSpell;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Quercitron
 */
public final class Desertion extends CardImpl {

    public Desertion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");

        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());

        // If an artifact or creature spell is countered this way, put that card onto the battlefield under your control instead of into its owner's graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new DesertionReplacementEffect()));
    }

    private Desertion(final Desertion card) {
        super(card);
    }

    @Override
    public Desertion copy() {
        return new Desertion(this);
    }
}

class DesertionReplacementEffect extends ReplacementEffectImpl {

    DesertionReplacementEffect() {
        super(Duration.WhileOnStack, Outcome.PutCardInPlay);
        staticText = "If an artifact or creature spell is countered this way, put that card onto the battlefield under your control instead of into its owner's graveyard";
    }

    private DesertionReplacementEffect(final DesertionReplacementEffect effect) {
        super(effect);
    }

    @Override
    public DesertionReplacementEffect copy() {
        return new DesertionReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zce = (ZoneChangeEvent) event;
        zce.setToZone(Zone.BATTLEFIELD);
        zce.setPlayerId(source.getControllerId());
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!Objects.equals(event.getSourceId(), source.getSourceId())
                || !(((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD)) {
            return false;
        }
        MageObject mageObject = game.getObject(event.getTargetId());
        return mageObject != null
                && (mageObject.isArtifact(game) || mageObject.isCreature(game));
    }
}
