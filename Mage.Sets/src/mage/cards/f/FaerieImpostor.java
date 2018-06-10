
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class FaerieImpostor extends CardImpl {

    public FaerieImpostor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Faerie Impostor enters the battlefield, sacrifice it unless you return another creature you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new FaerieImpostorEffect()));
    }

    public FaerieImpostor(final FaerieImpostor card) {
        super(card);
    }

    @Override
    public FaerieImpostor copy() {
        return new FaerieImpostor(this);
    }
}

class FaerieImpostorEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature you control");
    private static final String effectText = "sacrifice it unless you return another creature you control to its owner's hand";

    static {
        filter.add(new AnotherPredicate());
    }

    FaerieImpostorEffect() {
        super(Outcome.ReturnToHand);
        staticText = effectText;
    }

    FaerieImpostorEffect(FaerieImpostorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            boolean targetChosen = false;
            TargetPermanent target = new TargetPermanent(1, 1, filter, true);
            if (target.canChoose(controller.getId(), game) && controller.chooseUse(outcome, "Return another creature you control to its owner's hand?", source, game)) {
                controller.chooseTarget(Outcome.ReturnToHand, target, source, game);
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    targetChosen = true;
                    permanent.moveToZone(Zone.HAND, this.getId(), game, false);
                }
            }

            if (!targetChosen) {
                new SacrificeSourceEffect().apply(game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public FaerieImpostorEffect copy() {
        return new FaerieImpostorEffect(this);
    }

}
