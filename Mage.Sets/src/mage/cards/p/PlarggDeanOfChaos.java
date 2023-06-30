package mage.cards.p;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author htrajan
 */
public final class PlarggDeanOfChaos extends ModalDoubleFacedCard {

    private static final FilterCreaturePermanent tappedFilter = new FilterCreaturePermanent("tapped creatures you control");
    private static final FilterCreaturePermanent untappedFilter = new FilterCreaturePermanent("untapped creatures you control");

    static {
        tappedFilter.add(TappedPredicate.TAPPED);
        tappedFilter.add(TargetController.YOU.getControllerPredicate());

        untappedFilter.add(TappedPredicate.UNTAPPED);
        untappedFilter.add(TargetController.YOU.getControllerPredicate());
    }

    public PlarggDeanOfChaos(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ORC, SubType.SHAMAN}, "{1}{R}",
                "Augusta, Dean of Order",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.CLERIC}, "{2}{W}"
        );

        // 1.
        // Plargg, Dean of Chaos
        // Legendary Creature - Orc Shaman
        this.getLeftHalfCard().setPT(2, 2);

        // {T}, Discard a card: Draw a card.
        SimpleActivatedAbility rummageAbility = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new TapSourceCost());
        rummageAbility.addCost(new DiscardCardCost());
        this.getLeftHalfCard().addAbility(rummageAbility);

        // {4}{R}, {T}: Reveal cards from the top of your library until you reveal a nonlegendary, nonland card with mana value 3 or less. You may cast that card without paying its mana cost. Put all revealed cards not cast this way on the bottom of your library in a random order.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(new PlarggDeanOfChaosEffect(), new ManaCostsImpl<>("{4}{R}"));
        ability.addCost(new TapSourceCost());
        this.getLeftHalfCard().addAbility(ability);

        // 2.
        // Augusta, Dean of Order
        // Legendary Creature - Human Cleric
        this.getRightHalfCard().setPT(1, 3);

        // Other tapped creatures you control get +1/+0.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new BoostAllEffect(
                StaticValue.get(1), StaticValue.get(0), Duration.WhileOnBattlefield, tappedFilter, true)));

        // Other untapped creatures you control get +0/+1.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new BoostAllEffect(
                StaticValue.get(0), StaticValue.get(1), Duration.WhileOnBattlefield, untappedFilter, true)));

        // Whenever you attack, untap each creature you control, then tap any number of creatures you control.
        AttacksWithCreaturesTriggeredAbility augustaAbility = new AttacksWithCreaturesTriggeredAbility(
                new UntapAllControllerEffect(StaticFilters.FILTER_PERMANENT_CREATURES, "untap each creature you control"), 1);
        augustaAbility.addEffect(new AugustaDeanOfOrderEffect().concatBy(", then"));
        this.getRightHalfCard().addAbility(augustaAbility);
    }

    private PlarggDeanOfChaos(final PlarggDeanOfChaos card) {
        super(card);
    }

    @Override
    public PlarggDeanOfChaos copy() {
        return new PlarggDeanOfChaos(this);
    }
}

class PlarggDeanOfChaosEffect extends OneShotEffect {

    public PlarggDeanOfChaosEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "reveal cards from the top of your library until you reveal a "
                + "nonlegendary, nonland card with mana value 3 or less. "
                + "You may cast that card without paying its mana cost. Put all revealed "
                + "cards not cast this way on the bottom of your library in a random order";
    }

    public PlarggDeanOfChaosEffect(PlarggDeanOfChaosEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean cardWasCast = false;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && controller.getLibrary().hasCards()) {
            CardsImpl toReveal = new CardsImpl();
            Card eligibleCard = null;
            for (Card card : controller.getLibrary().getCards(game)) {
                toReveal.add(card);
                if (!card.isLand(game) && !card.isLegendary(game) && card.getManaValue() < 4) {
                    eligibleCard = card;
                    break;
                }
            }
            controller.revealCards(source, toReveal, game);
            if (eligibleCard != null
                    && controller.chooseUse(Outcome.PlayForFree, "Cast " + eligibleCard.getLogName() + " without paying its mana cost?", source, game)) {
                game.getState().setValue("PlayFromNotOwnHandZone" + eligibleCard.getId(), Boolean.TRUE);
                cardWasCast = controller.cast(controller.chooseAbilityForCast(eligibleCard, game, true),
                        game, true, new ApprovingObject(source, game));
                game.getState().setValue("PlayFromNotOwnHandZone" + eligibleCard.getId(), null);
                if (cardWasCast) {
                    toReveal.remove(eligibleCard);
                }
            }
            controller.putCardsOnBottomOfLibrary(toReveal, game, source, false);
        }
        return cardWasCast;
    }

    @Override
    public PlarggDeanOfChaosEffect copy() {
        return new PlarggDeanOfChaosEffect(this);
    }
}

class AugustaDeanOfOrderEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public AugustaDeanOfOrderEffect() {
        super(Outcome.Benefit);
        staticText = "tap any number of creatures you control";
    }

    public AugustaDeanOfOrderEffect(AugustaDeanOfOrderEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, filter, true);
        Player controller = game.getPlayer(source.getControllerId());
        controller.chooseTarget(Outcome.Benefit, target, source, game);
        target.getTargets().forEach(t -> {
            Permanent permanent = game.getPermanent(t);
            permanent.tap(source, game);
        });
        return true;
    }

    @Override
    public AugustaDeanOfOrderEffect copy() {
        return new AugustaDeanOfOrderEffect(this);
    }
}