
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.PlayerDamagedBySourceWatcher;

/**
 * @author nantuko
 */
public final class SteelHellkite extends CardImpl {

    public SteelHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
        // {2}: Steel Hellkite gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new GenericManaCost(2)));
        // {X}: Destroy each nonland permanent with converted mana cost X whose controller was dealt combat damage by Steel Hellkite this turn. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new SteelHellkiteDestroyEffect(), new ManaCostsImpl("{X}")));

    }

    public SteelHellkite(final SteelHellkite card) {
        super(card);
    }

    @Override
    public SteelHellkite copy() {
        return new SteelHellkite(this);
    }
}

class SteelHellkiteDestroyEffect extends OneShotEffect {

    public SteelHellkiteDestroyEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy each nonland permanent with converted mana cost X whose controller was dealt combat damage by {this} this turn";
    }

    public SteelHellkiteDestroyEffect(final SteelHellkiteDestroyEffect effect) {
        super(effect);
    }

    @Override
    public SteelHellkiteDestroyEffect copy() {
        return new SteelHellkiteDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = source.getManaCostsToPay().getX();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterNonlandPermanent(), source.getControllerId(), source.getSourceId(), game)) {
            if (permanent.getConvertedManaCost() == xValue) {
                PlayerDamagedBySourceWatcher watcher = (PlayerDamagedBySourceWatcher) game.getState().getWatchers().get(PlayerDamagedBySourceWatcher.class.getSimpleName(), permanent.getControllerId());
                if (watcher != null && watcher.hasSourceDoneDamage(source.getSourceId(), game)) {
                    permanent.destroy(source.getSourceId(), game, false);
                }
            }
        }
        return true;
    }
}
