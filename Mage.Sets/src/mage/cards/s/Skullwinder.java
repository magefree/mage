
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class Skullwinder extends CardImpl {

    public Skullwinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Skullwinder enters the battlefield, return target card from your graveyard to your hand, then choose an opponent. That player returns a card from their graveyard to their hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard());
        ability.addEffect(new SkullwinderEffect());
        this.addAbility(ability);
    }

    private Skullwinder(final Skullwinder card) {
        super(card);
    }

    @Override
    public Skullwinder copy() {
        return new Skullwinder(this);
    }
}

class SkullwinderEffect extends OneShotEffect {

    public SkullwinderEffect() {
        super(Outcome.Benefit);
        this.staticText = ", then choose an opponent. That player returns a card from their graveyard to their hand";
    }

    public SkullwinderEffect(final SkullwinderEffect effect) {
        super(effect);
    }

    @Override
    public SkullwinderEffect copy() {
        return new SkullwinderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            TargetOpponent targetOpponent = new TargetOpponent(true);
            if (controller.choose(Outcome.Detriment, targetOpponent, source, game)) {
                Player opponent = game.getPlayer(targetOpponent.getFirstTarget());
                if (opponent != null) {
                    game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " has chosen " + opponent.getLogName());
                    // That player returns a card from their graveyard to their hand
                    TargetCardInYourGraveyard targetCard = new TargetCardInYourGraveyard(new FilterCard("a card from your graveyard to return to your hand"));
                    targetCard.setNotTarget(true);
                    if (opponent.choose(outcome, targetCard, source, game)) {
                        Card card = game.getCard(targetCard.getFirstTarget());
                        if (card != null) {
                            opponent.moveCards(card, Zone.HAND, source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;

    }
}
