package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.hint.HintUtils;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.awt.Color;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author mllagostera, brahle
 */
public final class WinterCynicalOpportunist extends CardImpl {

    public WinterCynicalOpportunist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Winter attacks, mill three cards.
        this.addAbility(new AttacksTriggeredAbility(new MillCardsControllerEffect(3)));

        // Delirium — At the beginning of your end step, you may exile any number of cards from your graveyard with four or more card types among them. If you do, put a permanent card from among them onto the battlefield with a finality counter on it.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new WinterCynicalOpportunistEffect()
        ).setAbilityWord(AbilityWord.DELIRIUM).addHint(CardTypesInGraveyardCount.YOU.getHint()));
    }

    private WinterCynicalOpportunist(final WinterCynicalOpportunist card) {
        super(card);
    }

    @Override
    public WinterCynicalOpportunist copy() {
        return new WinterCynicalOpportunist(this);
    }
}

class WinterCynicalOpportunistEffect extends OneShotEffect {

    WinterCynicalOpportunistEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "you may exile any number of cards from your graveyard with four or more card types among them. "
                + "If you do, put a permanent card from among them onto the battlefield with a finality counter on it";
    }

    private WinterCynicalOpportunistEffect(final WinterCynicalOpportunistEffect effect) {
        super(effect);
    }

    @Override
    public WinterCynicalOpportunistEffect copy() {
        return new WinterCynicalOpportunistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !DeliriumCondition.instance.apply(game, source)) {
            return false;
        }
        if (!player.chooseUse(outcome, "Exile cards from your graveyard with four or more card types among them?", source, game)) {
            return false;
        }
        TargetCardInYourGraveyard target = new WinterCynicalOpportunistTarget();
        if (!player.choose(Outcome.Exile, target, source, game)
                || target.getTargets().isEmpty()
                || WinterCynicalOpportunistTarget.countCardTypes(target.getTargets(), game) < 4) {
            return false;
        }
        Cards exiledCards = new CardsImpl(target.getTargets());
        player.moveCards(exiledCards, Zone.EXILED, source, game);
        exiledCards.retainZone(Zone.EXILED, game);
        if (exiledCards.count(StaticFilters.FILTER_CARD_PERMANENT, game) > 0) {
            TargetCard targetPermanent = new TargetCard(1, 1, Zone.EXILED, StaticFilters.FILTER_CARD_PERMANENT);
            targetPermanent.withNotTarget(true);
            targetPermanent.setRequired(true);
            player.choose(Outcome.PutCardInPlay, exiledCards, targetPermanent, source, game);
            Card card = game.getCard(targetPermanent.getFirstTarget());
            if (card != null) {
                Counters counters = new Counters();
                counters.addCounter(CounterType.FINALITY.createInstance());
                game.setEnterWithCounters(card.getId(), counters);
                player.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
        }
        return true;
    }
}

class WinterCynicalOpportunistTarget extends TargetCardInYourGraveyard {

    WinterCynicalOpportunistTarget() {
        super(1, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_CARDS, true);
    }

    private WinterCynicalOpportunistTarget(final WinterCynicalOpportunistTarget target) {
        super(target);
    }

    @Override
    public WinterCynicalOpportunistTarget copy() {
        return new WinterCynicalOpportunistTarget(this);
    }

    @Override
    public boolean isChosen(Game game) {
        return super.isChosen(game) && countCardTypes(this.getTargets(), game) >= 4;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        if (!super.canChoose(sourceControllerId, source, game)) {
            return false;
        }
        Set<UUID> idsToCheck = new HashSet<>();
        idsToCheck.addAll(this.getTargets());
        idsToCheck.addAll(this.possibleTargets(sourceControllerId, source, game));
        return countCardTypes(idsToCheck, game) >= 4;
    }

    @Override
    public String getMessage(Game game) {
        int types = countCardTypes(this.getTargets(), game);
        return "Select cards from your graveyard with four or more card types among them (selected "
                + this.getTargets().size() + " cards; card types: "
                + HintUtils.prepareText(types + " of 4", types >= 4 ? Color.GREEN : Color.RED) + ")";
    }

    static int countCardTypes(Collection<UUID> cardIds, Game game) {
        return cardIds.stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .map(card -> card.getCardType(game))
                .flatMap(Collection::stream)
                .distinct()
                .mapToInt(x -> 1)
                .sum();
    }
}
