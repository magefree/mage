package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WickedGuardian extends CardImpl {

    public WickedGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Wicked Guardian enters the battlefield, you may have it deal 2 damage to another creature you control. If you do, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new WickedGuardianEffect(), true));
    }

    private WickedGuardian(final WickedGuardian card) {
        super(card);
    }

    @Override
    public WickedGuardian copy() {
        return new WickedGuardian(this);
    }
}

class WickedGuardianEffect extends OneShotEffect {

    WickedGuardianEffect() {
        super(Outcome.Benefit);
        staticText = "have it deal 2 damage to another creature you control. If you do, draw a card";
    }

    private WickedGuardianEffect(final WickedGuardianEffect effect) {
        super(effect);
    }

    @Override
    public WickedGuardianEffect copy() {
        return new WickedGuardianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (game.getBattlefield().count(StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL, source.getControllerId(), source, game) == 0) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, 1, StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL, true);
        if (!player.choose(outcome, target, source, game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        permanent.damage(2, source.getSourceId(), source, game);
        return player.drawCards(1, source, game) > 0;
    }
}
