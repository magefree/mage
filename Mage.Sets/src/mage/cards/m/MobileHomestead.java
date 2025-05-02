package mage.cards.m;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MobileHomestead extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.MOUNT));

    public MobileHomestead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Mobile Homestead has haste as long as you control a Mount.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield),
                condition, "{this} has haste as long as you control a Mount"
        )));

        // Whenever Mobile Homestead attacks, look at the top card of your library. If it's a land card, you may put it onto the battlefield tapped.
        this.addAbility(new AttacksTriggeredAbility(new MobileHomesteadEffect()));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private MobileHomestead(final MobileHomestead card) {
        super(card);
    }

    @Override
    public MobileHomestead copy() {
        return new MobileHomestead(this);
    }
}

class MobileHomesteadEffect extends OneShotEffect {

    MobileHomesteadEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. "
                + "If it's a land card, you may put it onto the battlefield tapped";
    }

    private MobileHomesteadEffect(final MobileHomesteadEffect effect) {
        super(effect);
    }

    @Override
    public MobileHomesteadEffect copy() {
        return new MobileHomesteadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return true;
        }
        controller.lookAtCards(sourceObject.getIdName(), new CardsImpl(card), game);
        if (!card.isLand(game)) {
            return true;
        }
        String message = "Put " + card.getLogName() + " onto the battlefield tapped?";
        if (controller.chooseUse(Outcome.PutLandInPlay, message, source, game)) {
            controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
        }
        return true;
    }
}
