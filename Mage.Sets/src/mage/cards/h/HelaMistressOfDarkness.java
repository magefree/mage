package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LoseLifeTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.dynamicvalue.common.SavedLifeLossValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.VengeanceCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author muz
 */
public final class HelaMistressOfDarkness extends VengeanceCard {

    public HelaMistressOfDarkness(UUID ownerId, CardSetInfo setInfo) {
        super(
            ownerId, setInfo,
            new CardType[]{CardType.CREATURE}, "{4}{B}{B}",
            "Dark Tutor",
            new CardType[]{CardType.SORCERY}, "{1}{B}{B}"
        );

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.subtype.add(SubType.SORCERER);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever a player loses life during your turn, you may create that many tapped 2/2 black Zombie creature tokens. Do this only once each turn.
        this.addAbility(new LoseLifeTriggeredAbility(
            new CreateTokenEffect(new ZombieToken(), SavedLifeLossValue.MANY, true, false),
            TargetController.ANY, true, false
        ).withTriggerCondition(MyTurnCondition.instance).setDoOnlyOnceEachTurn(true));

        // Dark Tutor
        // Sorcery - Vengeance
        // Search your library for a card, put it into your hand or graveyard, then shuffle. You lose 3 life.
        this.getSpellCard().getSpellAbility().addEffect(new HelaDarkTutorEffect());
        this.getSpellCard().getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(3));

        this.finalizeVengeance();
    }

    private HelaMistressOfDarkness(final HelaMistressOfDarkness card) {
        super(card);
    }

    @Override
    public HelaMistressOfDarkness copy() {
        return new HelaMistressOfDarkness(this);
    }
}

class HelaDarkTutorEffect extends OneShotEffect {

    HelaDarkTutorEffect() {
        super(Outcome.DrawCard);
        staticText = "search your library for a card, put it into your hand or graveyard, then shuffle";
    }

    private HelaDarkTutorEffect(final HelaDarkTutorEffect effect) {
        super(effect);
    }

    @Override
    public HelaDarkTutorEffect copy() {
        return new HelaDarkTutorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary();
        if (controller.searchLibrary(target, source, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                boolean toHand = controller.chooseUse(
                    Outcome.DrawCard, "Put " + card.getLogName() + " into your hand or graveyard?", null,
                    "Hand", "Graveyard", source, game
                );
                controller.moveCards(card, toHand ? Zone.HAND : Zone.GRAVEYARD, source, game);
            }
        }
        controller.shuffleLibrary(source, game);
        return true;
    }
}
