
package mage.cards.b;

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
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth using LevelX2 tech
 */
public final class BanisherPriest extends CardImpl {

    public BanisherPriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.HUMAN, SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Banisher Priest enters the battlefield, exile target creature an opponent controls until Banisher Priest leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BanisherPriestExileEffect());
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);
    }

    private BanisherPriest(final BanisherPriest card) {
        super(card);
    }

    @Override
    public BanisherPriest copy() {
        return new BanisherPriest(this);
    }
}

class BanisherPriestExileEffect extends OneShotEffect {

    public BanisherPriestExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile target creature an opponent controls until {this} leaves the battlefield. <i>(That creature returns under its owner's control.)</i>";
    }

    public BanisherPriestExileEffect(final BanisherPriestExileEffect effect) {
        super(effect);
    }

    @Override
    public BanisherPriestExileEffect copy() {
        return new BanisherPriestExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) source.getSourceObjectIfItStillExists(game);
        // If Banisher Priest leaves the battlefield before its triggered ability resolves,
        // the target creature won't be exiled.
        if (permanent != null) {
            return new ExileTargetEffect(CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()), permanent.getIdName()).apply(game, source);
        }
        return false;
    }
}
