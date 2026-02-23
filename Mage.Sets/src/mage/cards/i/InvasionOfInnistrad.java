package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfInnistrad extends TransformingDoubleFacedCard {

    public InvasionOfInnistrad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{2}{B}{B}",
                "Deluge of the Dead",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "B"
        );

        // Invasion of Innistrad
        this.getLeftHalfCard().setStartingDefense(5);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // Flash
        this.getLeftHalfCard().addAbility(FlashAbility.getInstance());

        // When Invasion of Innistrad enters the battlefield, target creature an opponent controls gets -13/-13 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(-13, -13));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.getLeftHalfCard().addAbility(ability);

        // Deluge of the Dead
        // When Deluge of the Dead enters the battlefield, create two 2/2 black Zombie creature tokens.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ZombieToken(), 2)));

        // {2}{B}: Exile target card from a graveyard. If it was a creature card, create a 2/2 black Zombie creature token.
        Ability rightAbility = new SimpleActivatedAbility(new DelugeOfTheDeadEffect(), new ManaCostsImpl<>("{2}{B}"));
        rightAbility.addTarget(new TargetCardInGraveyard());
        this.getRightHalfCard().addAbility(rightAbility);
    }

    private InvasionOfInnistrad(final InvasionOfInnistrad card) {
        super(card);
    }

    @Override
    public InvasionOfInnistrad copy() {
        return new InvasionOfInnistrad(this);
    }
}

class DelugeOfTheDeadEffect extends OneShotEffect {

    DelugeOfTheDeadEffect() {
        super(Outcome.Benefit);
        staticText = "exile target card from a graveyard. " +
                "If it was a creature card, create a 2/2 black Zombie creature token";
    }

    private DelugeOfTheDeadEffect(final DelugeOfTheDeadEffect effect) {
        super(effect);
    }

    @Override
    public DelugeOfTheDeadEffect copy() {
        return new DelugeOfTheDeadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        if (card.isCreature(game)) {
            new ZombieToken().putOntoBattlefield(1, game, source);
        }
        return true;
    }
}
