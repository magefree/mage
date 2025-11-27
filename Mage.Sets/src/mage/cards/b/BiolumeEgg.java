package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificeSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BiolumeEgg extends TransformingDoubleFacedCard {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.ISLAND, "Islands");

    public BiolumeEgg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SERPENT, SubType.EGG}, "{2}{U}",
                "Biolume Serpent",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SERPENT}, "U");

        // Biolume Egg
        this.getLeftHalfCard().setPT(0, 4);

        // Defender
        this.getLeftHalfCard().addAbility(DefenderAbility.getInstance());

        // When Biolume Egg enters the battlefield, scry 2.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(2)));

        // When you sacrifice Biolume Egg, return it to the battlefield transformed under its owner's control at the beginning of the next end step.
        this.getLeftHalfCard().addAbility(new SacrificeSourceTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new BiolumeEggEffect()), true
        ).setText("return it to the battlefield transformed under its owner's control at the beginning of the next end step"), false, true));

        // Biolume Serpent
        this.getRightHalfCard().setPT(4, 4);

        // Sacrifice two Islands: Biolume Serpent can't be blocked this turn.
        this.getRightHalfCard().addAbility(new SimpleActivatedAbility(
                new CantBeBlockedSourceEffect(Duration.EndOfTurn),
                new SacrificeTargetCost(2, filter)
        ));
    }

    private BiolumeEgg(final BiolumeEgg card) {
        super(card);
    }

    @Override
    public BiolumeEgg copy() {
        return new BiolumeEgg(this);
    }
}

class BiolumeEggEffect extends OneShotEffect {

    BiolumeEggEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return it to the battlefield transformed under your control";
    }

    private BiolumeEggEffect(final BiolumeEggEffect effect) {
        super(effect);
    }

    @Override
    public BiolumeEggEffect copy() {
        return new BiolumeEggEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null) {
            game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + card.getId(), Boolean.TRUE);
            controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, true, null);
        }
        return true;
    }
}
