package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.OneOrMoreCountersAddedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.AdaptAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class EmperorOfBones extends CardImpl {

    public EmperorOfBones(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of combat on your turn, exile up to one target card from a graveyard.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new ExileTargetEffect().setToSourceExileZone(true),
                TargetController.YOU, false
        );
        ability.addTarget(new TargetCardInGraveyard(0, 1));
        this.addAbility(ability);

        // {1}{B}: Adapt 2.
        this.addAbility(new AdaptAbility(2, "{1}{B}"));

        // Whenever one or more +1/+1 counters are put on Emperor of Bones, put a creature card exiled with Emperor of Bones onto the battlefield under your control with a finality counter on it. It gains haste. Sacrifice it at the beginning of the next end step.
        this.addAbility(new OneOrMoreCountersAddedTriggeredAbility(new EmperorOfBonesEffect()));
    }

    private EmperorOfBones(final EmperorOfBones card) {
        super(card);
    }

    @Override
    public EmperorOfBones copy() {
        return new EmperorOfBones(this);
    }
}

class EmperorOfBonesEffect extends OneShotEffect {

    EmperorOfBonesEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "put a creature card exiled with {this} onto the battlefield under your control with a finality counter on it. "
                + "It gains haste. Sacrifice it at the beginning of the next end step";
    }

    private EmperorOfBonesEffect(final EmperorOfBonesEffect effect) {
        super(effect);
    }

    @Override
    public EmperorOfBonesEffect copy() {
        return new EmperorOfBonesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInExile(
                1, 1,
                StaticFilters.FILTER_CARD_CREATURE,
                CardUtil.getExileZoneId(game, source)
        );
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return true;
        }
        game.setEnterWithCounters(card.getId(), new Counters().addCounter(CounterType.FINALITY.createInstance()));
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(CardUtil.getDefaultCardSideForBattlefield(game, card).getId());
        if (permanent == null) {
            return true;
        }
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfGame)
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new SacrificeTargetEffect("sacrifice it")
                        .setTargetPointer(new FixedTarget(permanent, game)),
                TargetController.ANY
        ), source);
        return true;
    }
}