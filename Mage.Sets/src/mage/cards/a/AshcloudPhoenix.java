
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class AshcloudPhoenix extends CardImpl {

    public AshcloudPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.PHOENIX);

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Ashcloud Phoenix dies, return it to the battlefield face down under your control.
        this.addAbility(new DiesSourceTriggeredAbility(new AshcloudPhoenixEffect()));

        // Morph {4}{R}{R}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{4}{R}{R}")));

        // When Ashcloud Phoenix is turned face up, it deals 2 damage to each player.
        Effect effect = new DamagePlayersEffect(2, TargetController.ANY);
        effect.setText("it deals 2 damage to each player");
        this.addAbility(new TurnedFaceUpSourceTriggeredAbility(effect));
    }

    private AshcloudPhoenix(final AshcloudPhoenix card) {
        super(card);
    }

    @Override
    public AshcloudPhoenix copy() {
        return new AshcloudPhoenix(this);
    }
}

class AshcloudPhoenixEffect extends OneShotEffect {

    AshcloudPhoenixEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "return it to the battlefield face down under your control";
    }

    AshcloudPhoenixEffect(final AshcloudPhoenixEffect effect) {
        super(effect);
    }

    @Override
    public AshcloudPhoenixEffect copy() {
        return new AshcloudPhoenixEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(source.getSourceId());
            if (card != null) {
                Player owner = game.getPlayer(card.getOwnerId());
                if (owner != null && owner.getGraveyard().contains(card.getId())) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, true, false, null);
                }
            }
            return true;
        }
        return false;
    }
}
