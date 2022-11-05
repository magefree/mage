package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInOpponentsGraveyard;

/**
 *
 * @author weirddan455
 */
public final class CarrionLocust extends CardImpl {

    private static final FilterCard filter = new FilterCard("card from an opponent's graveyard");

    public CarrionLocust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Carrion Locust enters the battlefield, exile target card from an opponent's graveyard. If it was a creature card, that player loses 1 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CarrionLocustEffect());
        ability.addTarget(new TargetCardInOpponentsGraveyard(filter));
        this.addAbility(ability);
    }

    private CarrionLocust(final CarrionLocust card) {
        super(card);
    }

    @Override
    public CarrionLocust copy() {
        return new CarrionLocust(this);
    }
}

class CarrionLocustEffect extends OneShotEffect {

    public CarrionLocustEffect() {
        super(Outcome.Exile);
        this.staticText = "exile target card from an opponent's graveyard. If it was a creature card, that player loses 1 life";
    }

    private CarrionLocustEffect(final CarrionLocustEffect effect) {
        super(effect);
    }

    @Override
    public CarrionLocustEffect copy() {
        return new CarrionLocustEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card targetCard = game.getCard(source.getFirstTarget());
        if (targetCard == null) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        boolean creature = targetCard.isCreature(game);
        Player owner = game.getPlayer(targetCard.getOwnerId());
        controller.moveCards(targetCard, Zone.EXILED, source, game);
        if (creature && owner != null) {
            owner.loseLife(1, game, source, false);
        }
        return true;
    }
}
