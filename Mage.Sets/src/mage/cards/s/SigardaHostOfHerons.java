package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author noxx
 */
public final class SigardaHostOfHerons extends CardImpl {

    public SigardaHostOfHerons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HexproofAbility.getInstance());

        // Spells and abilities your opponents control can't cause you to sacrifice permanents.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SigardaHostOfHeronsEffect()));
    }

    private SigardaHostOfHerons(final SigardaHostOfHerons card) {
        super(card);
    }

    @Override
    public SigardaHostOfHerons copy() {
        return new SigardaHostOfHerons(this);
    }
}

class SigardaHostOfHeronsEffect extends ContinuousRuleModifyingEffectImpl {

    public SigardaHostOfHeronsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Spells and abilities your opponents control can't cause you to sacrifice permanents";
    }

    public SigardaHostOfHeronsEffect(final SigardaHostOfHeronsEffect effect) {
        super(effect);
    }

    @Override
    public SigardaHostOfHeronsEffect copy() {
        return new SigardaHostOfHeronsEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICE_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        UUID eventSourceControllerId = game.getControllerId(event.getSourceId());
        Permanent permanent = game.getPermanent(event.getTargetId());

        if (controller != null && permanent != null && permanent.getControllerId() == source.getControllerId()) {
            return game.getOpponents(source.getControllerId()).contains(eventSourceControllerId);
        }
        return false;
    }

}
