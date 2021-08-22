package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EmptyToken;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class PhantomSteed extends CardImpl {

    public PhantomSteed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HORSE);
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Phantom Steed enters the battlefield, exile another target creature you control until Phantom Steed leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect("")
                .setText("exile another target creature you control until Phantom Steed leaves the battlefield"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);

        // Whenever Phantom Steed attacks, create a tapped and attacking token that's a copy of the exiled creature, except it's an Illusion in addition to its other types. Sacrifice that token at end of combat.
        this.addAbility(new AttacksTriggeredAbility(new PhantomSteedEffect()));
    }

    private PhantomSteed(final PhantomSteed card) {
        super(card);
    }

    @Override
    public PhantomSteed copy() {
        return new PhantomSteed(this);
    }
}

class PhantomSteedEffect extends OneShotEffect {

    PhantomSteedEffect() {
        super(Outcome.Benefit);
        staticText = "create a tapped and attacking token that's a copy of the exiled card, " +
                "except it's an Illusion in addition to its other types. Sacrifice that token at end of combat";
    }

    private PhantomSteedEffect(final PhantomSteedEffect effect) {
        super(effect);
    }

    @Override
    public PhantomSteedEffect copy() {
        return new PhantomSteedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone exileZone = game.getState().getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (exileZone == null || exileZone.isEmpty()) {
            return false;
        }
        for (Card card : exileZone.getCards(game)) {
            EmptyToken token = new EmptyToken();
            CardUtil.copyTo(token).from(card, game);
            token.addSubType(SubType.ILLUSION);
            token.putOntoBattlefield(1, game, source, source.getControllerId(), true, true);
            List<Permanent> permanents = token
                    .getLastAddedTokenIds()
                    .stream()
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            game.addDelayedTriggeredAbility(new AtTheEndOfCombatDelayedTriggeredAbility(
                    new ExileTargetEffect("Sacrifice that token at end of combat")
                            .setTargetPointer(new FixedTargets(permanents, game))
            ), source);
        }
        return true;
    }
}
