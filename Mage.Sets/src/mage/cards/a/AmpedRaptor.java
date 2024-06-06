package mage.cards.a;

import mage.ApprovingObject;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromHandSourcePermanentCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
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

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        ));
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
                List<Card> castableComponents = CardUtil.getCastableComponents(card, null, source, controller, game, null, false);
                if (castableComponents.isEmpty()) {
                    break;
                }
                String partsInfo = castableComponents
                        .stream()
                        .map(MageObject::getLogName)
                        .collect(Collectors.joining(" or "));
                if (!controller.chooseUse(Outcome.PlayForFree, "Cast spell by paying energy instead of mana (" + partsInfo + ")?", source, game)) {
                    break;
                }
                castableComponents.forEach(partCard -> game.getState().setValue("PlayFromNotOwnHandZone" + partCard.getId(), Boolean.TRUE));
                SpellAbility chosenAbility = controller.chooseAbilityForCast(card, game, true);
                if (chosenAbility != null) {
                    Card faceCard = game.getCard(chosenAbility.getSourceId());
                    if (faceCard != null) {
                        // pay energy instead of mana cost
                        PayEnergyCost energyCost = new PayEnergyCost(faceCard.getManaValue());
                        Costs<Cost> newCosts = new CostsImpl<>();
                        newCosts.add(energyCost);
                        newCosts.addAll(chosenAbility.getCosts());
                        controller.setCastSourceIdWithAlternateMana(faceCard.getId(), null, newCosts);
                        controller.cast(
                                chosenAbility, game, true,
                                new ApprovingObject(source, game)
                        );
                    }
                }
                castableComponents.forEach(partCard -> game.getState().setValue("PlayFromNotOwnHandZone" + partCard.getId(), null));
                break;
            }
        }
        return true;
    }
}