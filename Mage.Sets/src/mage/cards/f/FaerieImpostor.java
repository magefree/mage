package mage.cards.f;

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
 * @author LevelX2
 */
public final class FaerieImpostor extends CardImpl {

    public FaerieImpostor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Faerie Impostor enters the battlefield, sacrifice it unless you return another creature you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new FaerieImpostorEffect()));
    }

    private FaerieImpostor(final FaerieImpostor card) {
        super(card);
    }

    @Override
    public FaerieImpostor copy() {
        return new FaerieImpostor(this);
    }
}

class FaerieImpostorEffect extends OneShotEffect {

    FaerieImpostorEffect() {
        super(Outcome.ReturnToHand);
        staticText = "sacrifice it unless you return another creature you control to its owner's hand";
    }

    private FaerieImpostorEffect(FaerieImpostorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(1, 1, StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL, true);
        if (target.canChoose(controller.getId(), source, game)
                && controller.chooseUse(outcome, "Return another creature you control to its owner's hand?", source, game)) {
            controller.chooseTarget(Outcome.ReturnToHand, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                controller.moveCards(permanent, Zone.HAND, source, game);
                return true;
            }
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return permanent != null && permanent.sacrifice(source, game);
    }

    @Override
    public FaerieImpostorEffect copy() {
        return new FaerieImpostorEffect(this);
    }
}
