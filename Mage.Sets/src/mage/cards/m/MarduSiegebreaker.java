package mage.cards.m;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInExile;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;
import mage.util.functions.CopyTokenFunction;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MarduSiegebreaker extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("other target creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public MarduSiegebreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When this creature enters, exile up to one other target creature you control until this creature leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // Whenever this creature attacks, for each opponent, create a tapped token that's a copy of the exiled card attacking that opponent. At the beginning of your end step, sacrifice those tokens.
        this.addAbility(new AttacksTriggeredAbility(new MarduSiegebreakerEffect()));
    }

    private MarduSiegebreaker(final MarduSiegebreaker card) {
        super(card);
    }

    @Override
    public MarduSiegebreaker copy() {
        return new MarduSiegebreaker(this);
    }
}

class MarduSiegebreakerEffect extends OneShotEffect {

    MarduSiegebreakerEffect() {
        super(Outcome.Benefit);
        staticText = "for each opponent, create a tapped token that's a copy of the exiled card " +
                "attacking that opponent. At the beginning of your next end step, sacrifice those tokens";
    }

    private MarduSiegebreakerEffect(final MarduSiegebreakerEffect effect) {
        super(effect);
    }

    @Override
    public MarduSiegebreakerEffect copy() {
        return new MarduSiegebreakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards cards = game.getState().getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (cards == null) {
            return false;
        }
        Card card;
        switch (cards.size()) {
            case 0:
                return false;
            case 1:
                card = cards.getRandom(game);
                break;
            default:
                card = Optional.ofNullable(game.getPlayer(source.getControllerId())).map(player -> {
                    TargetCard target = new TargetCardInExile(StaticFilters.FILTER_CARD);
                    target.withNotTarget(true);
                    target.withChooseHint("to copy");
                    player.choose(Outcome.Neutral, cards, target, source, game);
                    return game.getCard(target.getFirstTarget());
                }).orElse(null);
        }
        if (card == null) {
            return false;
        }
        Set<MageObjectReference> addedTokens = new HashSet<>();
        Token token = CopyTokenFunction.createTokenCopy(card, game);
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            token.putOntoBattlefield(1, game, source, source.getControllerId(), true, true, opponentId);
            token.getLastAddedTokenIds()
                    .stream()
                    .map(uuid -> new MageObjectReference(uuid, game))
                    .forEach(addedTokens::add);
        }
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new SacrificeTargetEffect().setTargetPointer(new FixedTargets(addedTokens)), TargetController.YOU
        ), source);
        return true;
    }
}
