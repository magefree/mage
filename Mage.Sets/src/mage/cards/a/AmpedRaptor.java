package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class AmpedRaptor extends CardImpl {

    public AmpedRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // When Amped Raptor enters the battlefield, you get {E}{E}. Then if you cast it from your hand, exile cards from the top of your library until you exile a nonland card. You may cast that card by paying an amount of {E} equal to its mana value rather than paying its mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2));
        ability.addEffect(new ConditionalOneShotEffect(
                new AmpedRaptorEffect(),
                CastFromHandSourcePermanentCondition.instance
        ).concatBy("Then"));
        this.addAbility(ability, new CastFromHandWatcher());
    }

    private AmpedRaptor(final AmpedRaptor card) {
        super(card);
    }

    @Override
    public AmpedRaptor copy() {
        return new AmpedRaptor(this);
    }
}

class AmpedRaptorEffect extends OneShotEffect {

    AmpedRaptorEffect() {
        super(Outcome.PlayForFree);
        staticText = "exile cards from the top of your library until you exile a nonland card. "
                + "You may cast that card by paying an amount of {E} equal to its mana value rather than paying its mana cost";
    }

    private AmpedRaptorEffect(final AmpedRaptorEffect effect) {
        super(effect);
    }

    @Override
    public AmpedRaptorEffect copy() {
        return new AmpedRaptorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !controller.getLibrary().hasCards()) {
            return false;
        }
        for (Card card : controller.getLibrary().getCards(game)) {
            controller.moveCards(card, Zone.EXILED, source, game);
            if (!card.isLand(game)) {
                CardUtil.castSpellWithAttributesForCost(controller, source, game, card, null,
                        "Cast spell by paying energy instead of mana",
                        faceCard -> new PayEnergyCost(faceCard.getManaValue()));
                break;
            }
        }
        return true;
    }
}
