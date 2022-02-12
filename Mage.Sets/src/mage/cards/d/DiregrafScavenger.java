package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author weirddan455
 */
public final class DiregrafScavenger extends CardImpl {

    public DiregrafScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.BEAR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Diregraf Scavenger enters the battlefield, exile up to one target card from a graveyard. If a creature card was exiled this way, each opponent loses 2 life and you gain 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiregrafScavengerEffect());
        ability.addTarget(new TargetCardInGraveyard(0, 1));
        this.addAbility(ability);
    }

    private DiregrafScavenger(final DiregrafScavenger card) {
        super(card);
    }

    @Override
    public DiregrafScavenger copy() {
        return new DiregrafScavenger(this);
    }
}

class DiregrafScavengerEffect extends OneShotEffect {

    public DiregrafScavengerEffect() {
        super(Outcome.Exile);
        staticText = "exile up to one target card from a graveyard. If a creature card was exiled this way, each opponent loses 2 life and you gain 2 life";
    }

    private DiregrafScavengerEffect(final DiregrafScavengerEffect effect) {
        super(effect);
    }

    @Override
    public DiregrafScavengerEffect copy() {
        return new DiregrafScavengerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card targetCard = game.getCard(source.getFirstTarget());
        if (controller == null || targetCard == null) {
            return false;
        }
        boolean creature = targetCard.isCreature(game);
        if (!controller.moveCards(targetCard, Zone.EXILED, source, game)) {
            return false;
        }
        if (creature) {
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    opponent.loseLife(2, game, source, false);
                }
            }
            controller.gainLife(2, game, source);
        }
        return true;
    }
}
