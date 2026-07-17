package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.token.ClueArtifactToken;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NylaShirshuSleuth extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.CLUE, "you control no Clues"), ComparisonType.EQUAL_TO, 0
    );
    private static final Hint hint = new ValueHint(
            "Clues you control", new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.CLUE))
    );
    private static final FilterCard filter = new FilterCard("card exiled with this permanent");

    static {
        filter.add(NylaShirshuSleuthPredicate.instance);
    }

    public NylaShirshuSleuth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MOLE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Nyla enters, exile up to one target creature card from your graveyard. If you do, you lose X life and create X Clue tokens, where X is that card's mana value.
        Ability ability = new EntersBattlefieldTriggeredAbility(new NylaShirshuSleuthEffect());
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // At the beginning of your end step, if you control no Clues, return target card exiled with Nyla to its owner's hand.
        ability = new BeginningOfEndStepTriggeredAbility(new ReturnToHandTargetEffect()
                .setText("return target card exiled with {this} to its owner's hand"))
                .withInterveningIf(condition)
                .addHint(hint);
        ability.addTarget(new TargetCardInExile(filter));
        this.addAbility(ability);
    }

    private NylaShirshuSleuth(final NylaShirshuSleuth card) {
        super(card);
    }

    @Override
    public NylaShirshuSleuth copy() {
        return new NylaShirshuSleuth(this);
    }
}

enum NylaShirshuSleuthPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return Optional
                .ofNullable(input)
                .map(ObjectSourcePlayer::getSource)
                .map(source -> CardUtil.getExileZoneId(game, source))
                .map(game.getState().getExile()::getExileZone)
                .filter(exile -> exile.contains(input.getObject().getId()))
                .isPresent();
    }
}

class NylaShirshuSleuthEffect extends OneShotEffect {

    NylaShirshuSleuthEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to one target creature card from your graveyard. " +
                "If you do, you lose X life and create X Clue tokens, where X is that card's mana value";
    }

    private NylaShirshuSleuthEffect(final NylaShirshuSleuthEffect effect) {
        super(effect);
    }

    @Override
    public NylaShirshuSleuthEffect copy() {
        return new NylaShirshuSleuthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        int mv = card.getManaValue();
        player.moveCardsToExile(
                card, source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        if (mv > 0) {
            player.loseLife(mv, game, source, false);
            new ClueArtifactToken().putOntoBattlefield(mv, game, source);
        }
        return true;
    }
}
