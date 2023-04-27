package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateOnlyByOpponentActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CantBeRegeneratedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class ClergyOfTheHolyNimbus extends CardImpl {

    public ClergyOfTheHolyNimbus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // If Clergy of the Holy Nimbus would be destroyed, regenerate it.   
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new ClergyOfTheHolyNimbusReplacementEffect()));

        // {1}: Clergy of the Holy Nimbus can't be regenerated this turn. Only any opponent may activate this ability.
        this.addAbility(new ActivateOnlyByOpponentActivatedAbility(Zone.BATTLEFIELD, new CantBeRegeneratedSourceEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{1}")));
    }

    private ClergyOfTheHolyNimbus(final ClergyOfTheHolyNimbus card) {
        super(card);
    }

    @Override
    public ClergyOfTheHolyNimbus copy() {
        return new ClergyOfTheHolyNimbus(this);
    }
}

class ClergyOfTheHolyNimbusReplacementEffect extends ReplacementEffectImpl {

    ClergyOfTheHolyNimbusReplacementEffect() {
        super(Duration.Custom, Outcome.Regenerate);
        staticText = "If {this} would be destroyed, regenerate it";
    }

    ClergyOfTheHolyNimbusReplacementEffect(ClergyOfTheHolyNimbusReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent ClergyOfTheHolyNimbus = game.getPermanent(event.getTargetId());
        if (ClergyOfTheHolyNimbus != null
                && event.getAmount() == 0) { // 1=noRegen
            if (ClergyOfTheHolyNimbus.regenerate(source, game)) {
                game.informPlayers(source.getSourceObject(game).getName() + " has been regenerated.");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DESTROY_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId() != null
                && event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public ClergyOfTheHolyNimbusReplacementEffect copy() {
        return new ClergyOfTheHolyNimbusReplacementEffect(this);
    }

}
