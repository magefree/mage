
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiritToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class SekkiSeasonsGuide extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Spirits");

    static {
        filter.add(SubType.SPIRIT.getPredicate());
    }

    public SekkiSeasonsGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Sekki, Seasons' Guide enters the battlefield with eight +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(8)), "with eight +1/+1 counters on it"));

        // If damage would be dealt to Sekki, prevent that damage, remove that many +1/+1 counters from Sekki, and create that many 1/1 colorless Spirit creature tokens.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SekkiSeasonsGuideEffect()));

        // Sacrifice eight Spirits: Return Sekki from your graveyard to the battlefield.
        this.addAbility(new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToBattlefieldEffect(),
                new SacrificeTargetCost(new TargetControlledPermanent(8, 8, filter, true))));
    }

    private SekkiSeasonsGuide(final SekkiSeasonsGuide card) {
        super(card);
    }

    @Override
    public SekkiSeasonsGuide copy() {
        return new SekkiSeasonsGuide(this);
    }
}

class SekkiSeasonsGuideEffect extends PreventionEffectImpl {

    public SekkiSeasonsGuideEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false, false);
        staticText = "If damage would be dealt to {this}, prevent that damage, remove that many +1/+1 counters from {this}, and create that many 1/1 colorless Spirit creature tokens";
    }

    public SekkiSeasonsGuideEffect(final SekkiSeasonsGuideEffect effect) {
        super(effect);
    }

    @Override
    public SekkiSeasonsGuideEffect copy() {
        return new SekkiSeasonsGuideEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int damage = event.getAmount();
        preventDamageAction(event, source, game);
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.removeCounters(CounterType.P1P1.createInstance(damage), source, game);
        }
        new CreateTokenEffect(new SpiritToken(), damage).apply(game, source);
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getSourceId())) {
                return true;
            }
        }
        return false;
    }

}
