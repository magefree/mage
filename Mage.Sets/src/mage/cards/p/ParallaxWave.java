
package mage.cards.p;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.keyword.FadingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class ParallaxWave extends CardImpl {

    public ParallaxWave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}");

        // Fading 5
        this.addAbility(new FadingAbility(5, this));

        // Remove a fade counter from Parallax Wave: Exile target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetForSourceEffect(), new RemoveCountersSourceCost(CounterType.FADE.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // When Parallax Wave leaves the battlefield, each player returns to the battlefield all cards they own exiled with Parallax Wave.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ParallaxWaveEffect(), false));

    }

    private ParallaxWave(final ParallaxWave card) {
        super(card);
    }

    @Override
    public ParallaxWave copy() {
        return new ParallaxWave(this);
    }
}

class ParallaxWaveEffect extends OneShotEffect {

    public ParallaxWaveEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player returns to the battlefield all cards they own exiled with {this}";
    }

    public ParallaxWaveEffect(final ParallaxWaveEffect effect) {
        super(effect);
    }

    @Override
    public ParallaxWaveEffect copy() {
        return new ParallaxWaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        Player controller = game.getPlayer(source.getControllerId());
        if (sourceObject != null && controller != null) {
            int zoneChangeCounter = (sourceObject instanceof PermanentToken) ? source.getSourceObjectZoneChangeCounter() : source.getSourceObjectZoneChangeCounter() - 1;
            UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), zoneChangeCounter);
            if (exileZoneId != null) {
                ExileZone exileZone = game.getExile().getExileZone(exileZoneId);
                if (exileZone != null) {
                    return controller.moveCards(exileZone.getCards(game), Zone.BATTLEFIELD, source, game, false, false, true, null);
                }
                return true;
            }
        }
        return false;
    }
}
