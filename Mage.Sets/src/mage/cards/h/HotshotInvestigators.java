package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HotshotInvestigators extends CardImpl {

    public HotshotInvestigators(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Hotshot Investigators enters the battlefield, return up to one other target creature to its owner's hand. If you controlled it, investigate.
        Ability ability = new EntersBattlefieldTriggeredAbility(new HotshotInvestigatorsEffect());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);
    }

    private HotshotInvestigators(final HotshotInvestigators card) {
        super(card);
    }

    @Override
    public HotshotInvestigators copy() {
        return new HotshotInvestigators(this);
    }
}

class HotshotInvestigatorsEffect extends OneShotEffect {

    HotshotInvestigatorsEffect() {
        super(Outcome.Benefit);
        staticText = "return up to one other target creature to its owner's hand. If you controlled it, investigate";
    }

    private HotshotInvestigatorsEffect(final HotshotInvestigatorsEffect effect) {
        super(effect);
    }

    @Override
    public HotshotInvestigatorsEffect copy() {
        return new HotshotInvestigatorsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        boolean flag = permanent.isControlledBy(source.getControllerId());
        player.moveCards(permanent, Zone.HAND, source, game);
        if (flag) {
            new InvestigateEffect().apply(game, source);
        }
        return true;
    }
}
