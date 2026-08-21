package mage.cards.n;

import mage.ApprovingObject;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author notshauna
 */

public final class NicoMinoruRunaway extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell from anywhere other than your hand");
    
    static {
        filter.add(Predicates.not(new CastFromZonePredicate(Zone.HAND)));
    }

    public NicoMinoruRunaway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        //Whenever you cast a spell from anywhere other than your hand, Nico Minoru deals 2 damage to each opponent.
        this.addAbility(new SpellCastControllerTriggeredAbility(
        new DamagePlayersEffect(2, TargetController.OPPONENT),
        filter, false
        ));

        //{2}{R}, {T}, Discard a card: Exile cards from the top of your library until you exile a nonland card. You may cast that card without paying its mana cost.
        Ability ability = new SimpleActivatedAbility(
            new NicoMinoruRunawayEffect(),
            new ManaCostsImpl<>("{2}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardTargetCost(new TargetCardInHand()));
        this.addAbility(ability);
    }
    
    private NicoMinoruRunaway(final NicoMinoruRunaway card) {
        super(card);
    }

    @Override
    public NicoMinoruRunaway copy() {
        return new NicoMinoruRunaway(this);
    }

    class NicoMinoruRunawayEffect extends OneShotEffect {

        NicoMinoruRunawayEffect() {
            super(Outcome.PlayForFree);
            staticText = "Exile cards from the top of your library until you exile a nonland card." +
                    "You may cast that card without paying its mana cost.";
        }

        private NicoMinoruRunawayEffect(final NicoMinoruRunawayEffect effect) {
            super(effect);
        }

        @Override
        public NicoMinoruRunawayEffect copy() {
            return new NicoMinoruRunawayEffect(this);
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
                    if (!controller.chooseUse(Outcome.PlayForFree, "You may cast that card without paying its mana cost.(" + partsInfo + ")?", source, game)) {
                        break;
                    }
                    castableComponents.forEach(partCard -> game.getState().setValue("PlayFromNotOwnHandZone" + partCard.getId(), Boolean.TRUE));
                    SpellAbility chosenAbility = controller.chooseAbilityForCast(card, game, true);
                    if (chosenAbility != null) {
                        Card faceCard = game.getCard(chosenAbility.getSourceId());
                        if (faceCard != null) {
                            Costs<Cost> newCosts = new CostsImpl<>();
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
}