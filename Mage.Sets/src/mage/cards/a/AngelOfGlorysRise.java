
package mage.cards.a;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class AngelOfGlorysRise extends CardImpl {

    public AngelOfGlorysRise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        this.addAbility(FlyingAbility.getInstance());

        // When Angel of Glory's Rise enters the battlefield, exile all Zombies, then return all Human creature cards from your graveyard to the battlefield.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new AngelOfGlorysRiseEffect());
        this.addAbility(ability);
    }

    private AngelOfGlorysRise(final AngelOfGlorysRise card) {
        super(card);
    }

    @Override
    public AngelOfGlorysRise copy() {
        return new AngelOfGlorysRise(this);
    }
}

class AngelOfGlorysRiseEffect extends OneShotEffect {

    public AngelOfGlorysRiseEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "exile all Zombies, then return all Human creature cards from your graveyard to the battlefield";
    }

    public AngelOfGlorysRiseEffect(final AngelOfGlorysRiseEffect effect) {
        super(effect);
    }

    @Override
    public AngelOfGlorysRiseEffect copy() {
        return new AngelOfGlorysRiseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> toExile = new HashSet<>(game.getBattlefield()
                    .getActivePermanents(new FilterCreaturePermanent(SubType.ZOMBIE, "Zombie"), source.getControllerId(), source, game));
            controller.moveCards(toExile, Zone.EXILED, source, game);
            FilterCreatureCard filterHuman = new FilterCreatureCard();
            filterHuman.add(SubType.HUMAN.getPredicate());
            controller.moveCards(controller.getGraveyard().getCards(filterHuman, game), Zone.BATTLEFIELD, source, game);
        }
        return true;
    }
}
