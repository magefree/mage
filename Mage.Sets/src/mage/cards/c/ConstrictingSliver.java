package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class ConstrictingSliver extends CardImpl {

    public ConstrictingSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Sliver creatures you control have "When this creature enters the battlefield, you may exile target creature an opponent controls
        // until this creature leaves the battlefield."
        Ability ability = new EntersBattlefieldTriggeredAbility(new ConstrictingSliverExileEffect(), true);
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityControlledEffect(ability,
                        Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_ALL_SLIVERS)
                        .setText("Sliver creatures you control have \"When this creature enters the battlefield, "
                                + "you may exile target creature an opponent controls until this creature leaves the battlefield.\"")));

    }

    private ConstrictingSliver(final ConstrictingSliver card) {
        super(card);
    }

    @Override
    public ConstrictingSliver copy() {
        return new ConstrictingSliver(this);
    }
}

class ConstrictingSliverExileEffect extends OneShotEffect {

    public ConstrictingSliverExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may exile target creature an opponent controls until this creature leaves the battlefield";
    }

    public ConstrictingSliverExileEffect(final ConstrictingSliverExileEffect effect) {
        super(effect);
    }

    @Override
    public ConstrictingSliverExileEffect copy() {
        return new ConstrictingSliverExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        // If the creature leaves the battlefield before its triggered ability resolves,
        // the target creature won't be exiled.
        if (permanent != null) {
            return new ExileTargetEffect(CardUtil.getCardExileZoneId(game, source), permanent.getIdName()).apply(game, source);
        }
        return false;
    }
}
