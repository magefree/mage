package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ChoosePlayerEffect;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author grimreap124
 */
public final class SawhornNemesis extends CardImpl {

    public SawhornNemesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{3}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // As Sawhorn Nemesis enters the battlefield, choose a player.
        this.addAbility(new AsEntersBattlefieldAbility(new ChoosePlayerEffect(Outcome.Detriment)));
        // If a source would deal damage to the chosen player or a permanent they
        // control, it deals double that damage instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SawhornNemesisEffect()));
    }

    private SawhornNemesis(final SawhornNemesis card) {
        super(card);
    }

    @Override
    public SawhornNemesis copy() {
        return new SawhornNemesis(this);
    }
}

class SawhornNemesisEffect extends ReplacementEffectImpl {

    SawhornNemesisEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a source would deal damage to the chosen player or a permanent they control, it deals double that damage instead";
    }

    private SawhornNemesisEffect(final SawhornNemesisEffect effect) {
        super(effect);
    }

    @Override
    public SawhornNemesisEffect copy() {
        return new SawhornNemesisEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        UUID playerId = (UUID) game.getState().getValue(source.getSourceId() + "_player");
        if (playerId == null) {
            return false;
        }

        switch (event.getType()) {
            case DAMAGE_PLAYER:
                return playerId.equals(event.getTargetId());
            case DAMAGE_PERMANENT:
                Permanent permanent = game.getPermanent(event.getTargetId());
                if (permanent == null) {
                    return false;
                }
                return playerId.equals(permanent.getControllerId());
            default:
                return false;
        }
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }
}
