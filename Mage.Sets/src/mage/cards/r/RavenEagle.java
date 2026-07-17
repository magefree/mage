package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.ClueArtifactToken;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RavenEagle extends CardImpl {

    public RavenEagle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature enters or attacks, exile up to one target card from a graveyard. If a creature card is exiled this way, create a Clue token.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new RavenEagleEffect());
        ability.addTarget(new TargetCardInGraveyard(0, 1));
        this.addAbility(ability);

        // Whenever you draw your second card each turn, each opponent loses 1 life and you gain 1 life.
        ability = new DrawNthCardTriggeredAbility(new LoseLifeOpponentsEffect(1));
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private RavenEagle(final RavenEagle card) {
        super(card);
    }

    @Override
    public RavenEagle copy() {
        return new RavenEagle(this);
    }
}

class RavenEagleEffect extends OneShotEffect {

    RavenEagleEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to one target card from a graveyard. " +
                "If a creature card is exiled this way, create a Clue token";
    }

    private RavenEagleEffect(final RavenEagleEffect effect) {
        super(effect);
    }

    @Override
    public RavenEagleEffect copy() {
        return new RavenEagleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        boolean isCreature = card.isCreature(game);
        player.moveCards(card, Zone.EXILED, source, game);
        if (isCreature) {
            game.processAction();
            new ClueArtifactToken().putOntoBattlefield(1, game, source);
        }
        return true;
    }
}
