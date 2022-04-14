package mage.cards.a;

import java.util.HashSet;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class AllSeeingArbiter extends CardImpl {

    public AllSeeingArbiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever All-Seeing Arbiter enters the battlefield or attacks, draw two cards, then discard a card.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DrawDiscardControllerEffect(2, 1)));

        // Whenever you discard a card, target creature an opponent controls gets -X/-0 until your next turn, where X is the number of different mana values among cards in your graveyard.
        Ability ability = new DiscardCardControllerTriggeredAbility(new BoostTargetEffect(
                AllSeeingArbiterValue.instance, StaticValue.get(0), Duration.UntilYourNextTurn, true
        ), false);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private AllSeeingArbiter(final AllSeeingArbiter card) {
        super(card);
    }

    @Override
    public AllSeeingArbiter copy() {
        return new AllSeeingArbiter(this);
    }
}

enum AllSeeingArbiterValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller == null) {
            return 0;
        }
        HashSet<Integer> manaValues = new HashSet<>();
        for (UUID cardId : controller.getGraveyard()) {
            Card card = game.getCard(cardId);
            if (card != null) {
                manaValues.add(card.getManaValue());
            }
        }
        return -manaValues.size();
    }

    @Override
    public AllSeeingArbiterValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "-X";
    }

    @Override
    public String getMessage() {
        return "the number of different mana values among cards in your graveyard";
    }
}
