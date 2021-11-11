package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SacrificeSourceTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BiolumeEgg extends CardImpl {

    public BiolumeEgg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.SERPENT);
        this.subtype.add(SubType.EGG);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);
        this.secondSideCardClazz = mage.cards.b.BiolumeSerpent.class;

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // When Biolume Egg enters the battlefield, scry 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(2)));

        // When you sacrifice Biolume Egg, return it to the battlefield transformed under its owner's control at the beginning of the next end step.
        this.addAbility(new TransformAbility());
        this.addAbility(new SacrificeSourceTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ReturnLoyalCatharEffect()), true
        ).setText("return it to the battlefield transformed under its owner's control at the beginning of the next end step"), false));
    }

    private BiolumeEgg(final BiolumeEgg card) {
        super(card);
    }

    @Override
    public BiolumeEgg copy() {
        return new BiolumeEgg(this);
    }
}

class ReturnLoyalCatharEffect extends OneShotEffect {

    public ReturnLoyalCatharEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return it to the battlefield transformed under your control";
    }

    public ReturnLoyalCatharEffect(final ReturnLoyalCatharEffect effect) {
        super(effect);
    }

    @Override
    public ReturnLoyalCatharEffect copy() {
        return new ReturnLoyalCatharEffect(this);
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
