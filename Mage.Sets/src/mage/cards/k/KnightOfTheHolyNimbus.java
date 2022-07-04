package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateOnlyByOpponentActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CantBeRegeneratedSourceEffect;
import mage.abilities.keyword.FlankingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class KnightOfTheHolyNimbus extends CardImpl {

    public KnightOfTheHolyNimbus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flanking
        this.addAbility(new FlankingAbility());

        // If Knight of the Holy Nimbus would be destroyed, regenerate it.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new KnightOfTheHolyNimbusReplacementEffect()));

        // {2}: Knight of the Holy Nimbus can't be regenerated this turn. Only any opponent may activate this ability.
        this.addAbility(new ActivateOnlyByOpponentActivatedAbility(Zone.BATTLEFIELD, new CantBeRegeneratedSourceEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{2}")));

    }

    private KnightOfTheHolyNimbus(final KnightOfTheHolyNimbus card) {
        super(card);
    }

    @Override
    public KnightOfTheHolyNimbus copy() {
        return new KnightOfTheHolyNimbus(this);
    }
}

class KnightOfTheHolyNimbusReplacementEffect extends ReplacementEffectImpl {

    KnightOfTheHolyNimbusReplacementEffect() {
        super(Duration.Custom, Outcome.Regenerate);
        staticText = "If {this} would be destroyed, regenerate it";
    }

    KnightOfTheHolyNimbusReplacementEffect(KnightOfTheHolyNimbusReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent knightOfTheHolyNimbus = game.getPermanent(event.getTargetId());
        if (knightOfTheHolyNimbus != null
                && event.getAmount() == 0) { // 1=noRegen
            if (knightOfTheHolyNimbus.regenerate(source, game)) {
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
    public KnightOfTheHolyNimbusReplacementEffect copy() {
        return new KnightOfTheHolyNimbusReplacementEffect(this);
    }

}