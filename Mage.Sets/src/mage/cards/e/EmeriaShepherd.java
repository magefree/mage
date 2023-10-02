
package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class EmeriaShepherd extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("nonland permanent card from your graveyard");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public EmeriaShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // <i>Landfall</i> &mdash; Whenever a land enters the battlefield under your control, you may return target nonland permanent card from your graveyard to your hand.
        // If that land is a Plains, you may return that nonland permanent card to the battlefield instead.
        Ability ability = new LandfallAbility(Zone.BATTLEFIELD, new EmeriaShepherdReturnToHandTargetEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private EmeriaShepherd(final EmeriaShepherd card) {
        super(card);
    }

    @Override
    public EmeriaShepherd copy() {
        return new EmeriaShepherd(this);
    }
}

class EmeriaShepherdReturnToHandTargetEffect extends OneShotEffect {

    public EmeriaShepherdReturnToHandTargetEffect() {
        super(Outcome.ReturnToHand);
        staticText = "you may return target nonland permanent card from your graveyard to your hand. If that land is a Plains, you may return that nonland permanent card to the battlefield instead";
    }

    private EmeriaShepherdReturnToHandTargetEffect(final EmeriaShepherdReturnToHandTargetEffect effect) {
        super(effect);
    }

    @Override
    public EmeriaShepherdReturnToHandTargetEffect copy() {
        return new EmeriaShepherdReturnToHandTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent triggeringLand = ((LandfallAbility) source).getTriggeringPermanent();
        if (controller == null || triggeringLand == null) {
            return false;
        }
        Zone toZone = Zone.HAND;
        if (triggeringLand.hasSubtype(SubType.PLAINS, game)
                && controller.chooseUse(Outcome.PutCardInPlay, "Put the card to battlefield instead?", source, game)) {
            toZone = Zone.BATTLEFIELD;
        }
        return controller.moveCards(new CardsImpl(targetPointer.getTargets(game, source)), toZone, source, game);
    }

}
