package mage.cards.d;

import mage.abilities.Ability;
import java.util.UUID;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class Daydream extends CardImpl {

    public Daydream(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}");

        // Exile target creature you control, then return that card to the battlefield under its owner's control with a +1/+1 counter on it.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new DaydreamEffect());

        // Flashback {2}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{2}{W}")));
    }

    private Daydream(final Daydream card) {
        super(card);
    }

    @Override
    public Daydream copy() {
        return new Daydream(this);
    }
}

class DaydreamEffect extends OneShotEffect {

    DaydreamEffect() {
        super(Outcome.Benefit);
        staticText = "Exile target creature you control, then return that card to the battlefield under its owner's control with a +1/+1 counter on it";
    }

    private DaydreamEffect(final DaydreamEffect effect) {
        super(effect);
    }

    @Override
    public DaydreamEffect copy() {
        return new DaydreamEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (permanent != null && controller != null) {
            UUID exileId = CardUtil.getExileZoneId("DaydreamEffectExile" + source.toString(), game);
            if (controller.moveCardsToExile(permanent, source, game, true, exileId, "")) {
                if (game.getExile().getExileZone(exileId) != null) {
                    Card exiledCard = game.getExile().getExileZone(exileId).get(permanent.getId(), game);
                    if (exiledCard != null) {
                        Counters countersToAdd = new Counters();
                        countersToAdd.addCounter(CounterType.P1P1.createInstance());
                        game.setEnterWithCounters(exiledCard.getId(), countersToAdd);
                        return controller.moveCards(exiledCard, Zone.BATTLEFIELD, source, game, false, false, true, null);
                    }
                }
            }
        }
        return false;
    }
}
