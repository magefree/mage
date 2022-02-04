package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
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
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class DrakeFamiliar extends CardImpl {

    public DrakeFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Drake Familiar enters the battlefield, sacrifice it unless you return an enchantment to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrakeFamiliarEffect()));
    }

    private DrakeFamiliar(final DrakeFamiliar card) {
        super(card);
    }

    @Override
    public DrakeFamiliar copy() {
        return new DrakeFamiliar(this);
    }
}

class DrakeFamiliarEffect extends OneShotEffect {

    DrakeFamiliarEffect() {
        super(Outcome.Sacrifice);
        staticText = "sacrifice it unless you return an enchantment to its owner's hand.";
    }

    private DrakeFamiliarEffect(DrakeFamiliarEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(StaticFilters.FILTER_PERMANENT_ENCHANTMENT);
        target.setNotTarget(true);
        if (target.canChoose(source.getSourceId(), controller.getId(), game)
                && controller.chooseUse(outcome, "Return an enchantment to its owner's hand?", source, game)) {
            controller.chooseTarget(Outcome.ReturnToHand, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                return controller.moveCards(permanent, Zone.HAND, source, game);
            }
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return permanent != null && permanent.sacrifice(source, game);
    }

    @Override
    public DrakeFamiliarEffect copy() {
        return new DrakeFamiliarEffect(this);
    }
}
