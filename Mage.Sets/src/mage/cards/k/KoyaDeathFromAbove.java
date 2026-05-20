package mage.cards.k;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class KoyaDeathFromAbove extends CardImpl {

    public KoyaDeathFromAbove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Koya enters, exile up to one other target creature. At the beginning of the next end step, you may pay {3}{B}. If you don't, return that card to the battlefield under its owner's control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new KoyaDeathFromAboveEffect());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);
    }

    private KoyaDeathFromAbove(final KoyaDeathFromAbove card) {
        super(card);
    }

    @Override
    public KoyaDeathFromAbove copy() {
        return new KoyaDeathFromAbove(this);
    }
}

class KoyaDeathFromAboveEffect extends OneShotEffect {

    KoyaDeathFromAboveEffect() {
        super(Outcome.Exile);
        this.staticText = "exile up to one other target creature. At the beginning of the next end step, "
                + "you may pay {3}{B}. If you don't, return that card to the battlefield under its owner's control";
    }

    private KoyaDeathFromAboveEffect(final KoyaDeathFromAboveEffect effect) {
        super(effect);
    }

    @Override
    public KoyaDeathFromAboveEffect copy() {
        return new KoyaDeathFromAboveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> toExile = getTargetPointer().getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        if (toExile.isEmpty()) {
            return false;
        }
        controller.moveCardsToExile(toExile, source, game, true,
                CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source));
        game.processAction();
        ReturnToBattlefieldUnderOwnerControlTargetEffect returnEffect =
                new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, true);
        DoIfCostPaid doIfCostPaid = new DoIfCostPaid(null, returnEffect, new ManaCostsImpl<>("{3}{B}"));
        doIfCostPaid.setTargetPointer(new FixedTargets(
                toExile.stream().map(Card::getMainCard).collect(Collectors.toSet()), game));
        game.addDelayedTriggeredAbility(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(doIfCostPaid), source);
        return true;
    }
}
