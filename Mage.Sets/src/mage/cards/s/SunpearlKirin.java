package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SunpearlKirin extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("other target nonland permanent you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public SunpearlKirin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.KIRIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, return up to one other target nonland permanent you control to its owner's hand. If it was a token, draw a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SunpearlKirinEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);
    }

    private SunpearlKirin(final SunpearlKirin card) {
        super(card);
    }

    @Override
    public SunpearlKirin copy() {
        return new SunpearlKirin(this);
    }
}

class SunpearlKirinEffect extends OneShotEffect {

    SunpearlKirinEffect() {
        super(Outcome.Benefit);
        staticText = "return up to one other target nonland permanent " +
                "you control to its owner's hand. If it was a token, draw a card";
    }

    private SunpearlKirinEffect(final SunpearlKirinEffect effect) {
        super(effect);
    }

    @Override
    public SunpearlKirinEffect copy() {
        return new SunpearlKirinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        boolean isToken = permanent instanceof PermanentToken;
        player.moveCards(permanent, Zone.HAND, source, game);
        if (isToken) {
            player.drawCards(1, source, game);
        }
        return true;
    }
}
