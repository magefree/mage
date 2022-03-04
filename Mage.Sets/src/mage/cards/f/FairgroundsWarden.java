
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author fireshoes
 */
public final class FairgroundsWarden extends CardImpl {

    public FairgroundsWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Fairgrounds Warden enters the battlefield, exile target creature an opponent controls until Fairgrounds Warden leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new FairsgroundsWardenExileEffect());
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);
    }

    private FairgroundsWarden(final FairgroundsWarden card) {
        super(card);
    }

    @Override
    public FairgroundsWarden copy() {
        return new FairgroundsWarden(this);
    }
}

class FairsgroundsWardenExileEffect extends OneShotEffect {

    public FairsgroundsWardenExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile target creature an opponent controls until {this} leaves the battlefield.";
    }

    public FairsgroundsWardenExileEffect(final FairsgroundsWardenExileEffect effect) {
        super(effect);
    }

    @Override
    public FairsgroundsWardenExileEffect copy() {
        return new FairsgroundsWardenExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) source.getSourceObjectIfItStillExists(game);
        // If Fairsgrounds Warden leaves the battlefield before its triggered ability resolves,
        // the target creature won't be exiled.
        if (permanent != null) {
            return new ExileTargetEffect(CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()), permanent.getIdName()).apply(game, source);
        }
        return false;
    }
}
