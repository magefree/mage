package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.keyword.DeathtouchAbility;
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
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author mllagostera
 */
public final class WinterCynicalOpportunist extends CardImpl {

    public WinterCynicalOpportunist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{2}{G}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Winter attacks, mill three cards.
        this.addAbility(new AttacksTriggeredAbility(new MillCardsControllerEffect(3), false));

        // Delirium - At the beginning of your end step, you may exile any number of
        // cards from your graveyard
        // with four or more card types among them. If you do, put a permanent card from
        // among them onto the battlefield with a finality counter on it.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new WinterCynicalOpportunistEffect());
        ability.setAbilityWord(AbilityWord.DELIRIUM);
        this.addAbility(ability);
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
        super(Outcome.Benefit);
        staticText = "you may exile any number of cards from your graveyard with four or more card types among them. " +
                "If you do, put a permanent card from among them onto the battlefield with a finality counter on it.";
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
        if (player == null) {
            return false;
        }

        if (player.getGraveyard().isEmpty()) {
            return false;
        }

        if (!DeliriumCondition.instance.apply(game, source)) {
            return false;
        }

        if (!player.chooseUse(Outcome.Benefit,
                "Exile any number of cards from your graveyard with four or more card types among them?", source,
                game)) {
            return false;
        }

        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(0, Integer.MAX_VALUE,
                StaticFilters.FILTER_CARDS_FROM_YOUR_GRAVEYARD);
        if (target.getTargets().isEmpty()) {
            return false;
        }

        Cards toExile = new CardsImpl();
        java.util.Set<CardType> types = new java.util.HashSet<>();

        for (UUID cardId : target.getTargets()) {
            Card card = player.getGraveyard().get(cardId, game);
            if (card == null) {
                continue;
            }
            toExile.add(card);
            types.addAll(card.getCardType(game));
        }

        if (types.size() < 4) {
            return false;
        }

        player.moveCards(toExile, Zone.EXILED, source, game);
        game.processAction();

        Cards exiledPermanents = new CardsImpl();
        for (UUID cardId : toExile) {
            Card card = game.getCard(cardId);
            if (card != null && card.isPermanent(game)) {
                exiledPermanents.add(card);
            }
        }

        if (exiledPermanents.isEmpty()) {
            return true;
        }

        Card chosen = null;
        if (exiledPermanents.size() == 1) {
            UUID cardId = exiledPermanents.iterator().next();
            chosen = game.getCard(cardId);
        } else {
            TargetCard target2 = new TargetCard(1, 1, Zone.EXILED, new FilterPermanentCard());
            boolean chooseResult2 = player.choose(Outcome.PutCardInPlay, exiledPermanents, target2, source, game);
            if (chooseResult2 && !target2.getTargets().isEmpty()) {
                chosen = game.getCard(target2.getFirstTarget());
            }
        }

        if (chosen != null) {
            player.moveCards(chosen, Zone.BATTLEFIELD, source, game);
            Permanent perm = game.getPermanent(chosen.getId());
            if (perm != null) {
                perm.addCounters(CounterType.FINALITY.createInstance(), source.getControllerId(), source, game);
            }
        }

        return true;
    }
}
