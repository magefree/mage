package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.target.targetadjustment.TargetAdjuster;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class ShelobDreadWeaver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a nontoken creature an opponent controls");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public ShelobDreadWeaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER, SubType.DEMON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a nontoken creature an opponent controls dies, exile it.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new ShelobDreadWeaverExileEffect(), false, filter, true
        ));

        // {2}{B}, Put a creature card exiled with Shelob, Dread Weaver into its owner's graveyard: Put two +1/+1 counters on Shelob. Draw a card.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD, new AddCountersSourceEffect(
                        CounterType.P1P1.createInstance(2)
                ), new ManaCostsImpl<>("{2}{B}")
        );
        ability.addCost(new ShelobDreadWeaverCost());
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        this.addAbility(ability);

        // {X}{1}{B}: Put target creature card with mana value X exiled with Shelob onto the battlefield tapped under your control.
        Ability ability2 = new SimpleActivatedAbility(new ReturnToBattlefieldUnderYourControlTargetEffect(false, true)
                .setText("Put target creature card with mana value X exiled with {this} onto the battlefield tapped under your control"),
                new ManaCostsImpl<>("{X}{1}{B}"));
        ability2.setTargetAdjuster(ShelobDreadWeaverAdjuster.instance);
        this.addAbility(ability2);
    }

    private ShelobDreadWeaver(final ShelobDreadWeaver card) {
        super(card);
    }

    @Override
    public ShelobDreadWeaver copy() {
        return new ShelobDreadWeaver(this);
    }
}

class ShelobDreadWeaverExileEffect extends OneShotEffect {

    ShelobDreadWeaverExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile it";
    }

    private ShelobDreadWeaverExileEffect(final ShelobDreadWeaverExileEffect effect) {
        super(effect);
    }

    @Override
    public ShelobDreadWeaverExileEffect copy() {
        return new ShelobDreadWeaverExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Card card = game.getCard(targetPointer.getFirst(game, source));
        if (player == null || permanent == null || card == null) {
            return false;
        }
        player.moveCardsToExile(
                card, source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        return true;
    }
}

class ShelobDreadWeaverCost extends CostImpl {

    public ShelobDreadWeaverCost() {
        this.text = "Put a creature card exiled with {this} into its owner's graveyard";
    }

    public ShelobDreadWeaverCost(ShelobDreadWeaverCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            TargetCardInExile target = new TargetCardInExile(new FilterCreatureCard(), CardUtil.getCardExileZoneId(game, ability));
            target.setNotTarget(true);
            Cards cards = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, ability));
            if (cards != null
                    && !cards.isEmpty()
                    && controller.choose(Outcome.Benefit, cards, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    if (controller.moveCardToGraveyardWithInfo(card, source, game, Zone.EXILED)) {
                        paid = true;
                    }
                }
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player player = game.getPlayer(controllerId);
        Cards cards = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, ability));
        return player != null && cards != null &&
                cards.getCards(game)
                        .stream()
                        .anyMatch(card -> card.isCreature(game));
    }

    @Override
    public ShelobDreadWeaverCost copy() {
        return new ShelobDreadWeaverCost(this);
    }
}

enum ShelobDreadWeaverAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        FilterCard filter = new FilterCreatureCard("creature card with mana value " + xValue);
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        ability.addTarget(new TargetCardInExile(filter, CardUtil.getExileZoneId(game, ability)));
    }
}
