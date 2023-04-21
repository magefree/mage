package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;

/**
 * @author xenohedron
 */

public final class SoulgorgerOrgg extends CardImpl {

    public SoulgorgerOrgg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.ORGG);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Soulgorger Orgg enters the battlefield, you lose all but 1 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SoulgorgerOrggLoseLifeEffect()));

        // When Soulgorger Orgg leaves the battlefield, you gain life equal to the life you lost when it entered the battlefield.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new SoulgorgerOrggGainLifeEffect(), false));

    }

    private SoulgorgerOrgg(final SoulgorgerOrgg card) {
        super(card);
    }

    @Override
    public SoulgorgerOrgg copy() {
        return new SoulgorgerOrgg(this);
    }
}

class SoulgorgerOrggLoseLifeEffect extends OneShotEffect {

    public SoulgorgerOrggLoseLifeEffect() {
        super(Outcome.LoseLife);
        staticText = "you lose all but 1 life";
    }

    public SoulgorgerOrggLoseLifeEffect(final SoulgorgerOrggLoseLifeEffect effect) {
        super(effect);
    }

    @Override
    public SoulgorgerOrggLoseLifeEffect copy() {
        return new SoulgorgerOrggLoseLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int lifeValue = 0;
            if (player.getLife() > 1) {
                lifeValue = player.getLife() - 1;
            }
            game.getState().setValue(source.getSourceId().toString() + source.getControllerId().toString() + source.getSourceObjectZoneChangeCounter() + "_lifeValue", lifeValue);
            if (lifeValue > 0) {
                player.loseLife(lifeValue, game, source, false);
            }
        }
        return true;
    }

}

class SoulgorgerOrggGainLifeEffect extends OneShotEffect {

    public SoulgorgerOrggGainLifeEffect() {
        super(Outcome.GainLife);
        staticText = "you gain life equal to the life you lost when it entered the battlefield";
    }

    public SoulgorgerOrggGainLifeEffect(final SoulgorgerOrggGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public SoulgorgerOrggGainLifeEffect copy() {
        return new SoulgorgerOrggGainLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Object obj = game.getState().getValue(source.getSourceId().toString() + source.getControllerId().toString() + (source.getSourceObjectZoneChangeCounter() - 1) + "_lifeValue");
        if (!(obj instanceof Integer)) {
            return false;
        }
        int lifeValue = (int) obj;
        if (player != null && lifeValue > 0) {
            player.gainLife(lifeValue, game, source);
        }
        return true;
    }

}