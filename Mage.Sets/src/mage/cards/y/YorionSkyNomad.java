package mage.cards.y;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.keyword.CompanionAbility;
import mage.abilities.keyword.CompanionCondition;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;
import mage.util.ExileUtil;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class YorionSkyNomad extends CardImpl {

    public YorionSkyNomad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] {CardType.CREATURE}, "{3}{W/U}{W/U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Companion — Your starting deck contains at least twenty cards more than the minimum deck size.
        this.addAbility(new CompanionAbility(YorionSkyNomadCompanionCondition.instance));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Yorion enters the battlefield, exile any number of other nonland permanents you own
        // and control. Return those cards to the battlefield at the beginning of the next end step.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new YorionSkyNomadEffect()));
    }

    private YorionSkyNomad(final YorionSkyNomad card) {
        super(card);
    }

    @Override
    public YorionSkyNomad copy() {
        return new YorionSkyNomad(this);
    }
}

enum YorionSkyNomadCompanionCondition implements CompanionCondition {
    instance;

    @Override
    public String getRule() {
        return "Your starting deck contains at least twenty cards more than the minimum deck size.";
    }

    @Override
    public boolean isLegal(Set<Card> deck, int minimumDeckSize) {
        return deck.size() >= minimumDeckSize + 20;
    }
}

class YorionSkyNomadEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent("other nonland permanents you own and control");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(TargetController.YOU.getOwnerPredicate());
        filter.add(AnotherPredicate.instance);
    }

    YorionSkyNomadEffect() {
        super(Outcome.Benefit);
        staticText = "exile any number of other nonland permanents you own and control. " +
                "Return those cards to the battlefield at the beginning of the next end step.";
    }

    private YorionSkyNomadEffect(final YorionSkyNomadEffect effect) {
        super(effect);
    }

    @Override
    public YorionSkyNomadEffect copy() {
        return new YorionSkyNomadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject == null || controller == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, Integer.MAX_VALUE, filter, true);
        controller.choose(outcome, target, source, game);
        Set<Card> toExile = target.getTargets().stream().map(game::getPermanent).collect(Collectors.toSet());
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        controller.moveCardsToExile(toExile, source, game, true, exileId, sourceObject.getIdName());

        Cards cardsToReturn = ExileUtil.returnCardsFromExile(toExile, game);

        Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
        effect.setTargetPointer(new FixedTargets(cardsToReturn, game));
        AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }
}
