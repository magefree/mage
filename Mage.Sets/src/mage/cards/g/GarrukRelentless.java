package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WolfToken;
import mage.game.permanent.token.WolfDeathtouchToken;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class GarrukRelentless extends TransformingDoubleFacedCard {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE);

    public GarrukRelentless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.GARRUK}, "{3}{G}",
                "Garruk, the Veil-Cursed",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.GARRUK}, "BG"
        );

        // Garruk Relentless
        this.getLeftHalfCard().setStartingLoyalty(3);

        // When Garruk Relentless has two or fewer loyalty counters on him, transform him.
        this.getLeftHalfCard().addAbility(new GarrukRelentlessStateTrigger());

        // 0: Garruk Relentless deals 3 damage to target creature. That creature deals damage equal to its power to him.
        Ability ability = new LoyaltyAbility(new DamageTargetEffect(3), 0);
        ability.addEffect(new GarrukRelentlessDamageEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.getLeftHalfCard().addAbility(ability);

        // 0: Create a 2/2 green Wolf creature token.
        this.getLeftHalfCard().addAbility(new LoyaltyAbility(new CreateTokenEffect(new WolfToken()), 0));

        // Garruk, the Veil-Cursed
        // +1: Create a 1/1 black Wolf creature token with deathtouch.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new CreateTokenEffect(new WolfDeathtouchToken()), 1));

        // -1: Sacrifice a creature. If you do, search your library for a creature card, reveal it, put it into your hand, then shuffle.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new DoIfCostPaid(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(
                        StaticFilters.FILTER_CARD_CREATURE_A
                ), true),
                null,
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE),
                false
        ), -1));

        // -3: Creatures you control gain trample and get +X/+X until end of turn, where X is the number of creature cards in your graveyard.
        Ability backAbility = new LoyaltyAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("creatures you control gain trample"), -3);
        backAbility.addEffect(new BoostControlledEffect(
                xValue, xValue, Duration.EndOfTurn
        ).setText("and get +X/+X until end of turn, where X is the number of creature cards in your graveyard"));
        this.getRightHalfCard().addAbility(backAbility);
    }

    private GarrukRelentless(final GarrukRelentless card) {
        super(card);
    }

    @Override
    public GarrukRelentless copy() {
        return new GarrukRelentless(this);
    }
}

class GarrukRelentlessStateTrigger extends StateTriggeredAbility {

    GarrukRelentlessStateTrigger() {
        super(Zone.BATTLEFIELD, new TransformSourceEffect());
    }

    private GarrukRelentlessStateTrigger(final GarrukRelentlessStateTrigger ability) {
        super(ability);
    }

    @Override
    public GarrukRelentlessStateTrigger copy() {
        return new GarrukRelentlessStateTrigger(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        return permanent != null && permanent.getCounters(game).getCount(CounterType.LOYALTY) < 3;
    }

    @Override
    public String getRule() {
        return "When {this} has two or fewer loyalty counters on him, transform him.";
    }
}

class GarrukRelentlessDamageEffect extends OneShotEffect {

    GarrukRelentlessDamageEffect() {
        super(Outcome.Damage);
        staticText = "That creature deals damage equal to its power to him";
    }

    private GarrukRelentlessDamageEffect(final GarrukRelentlessDamageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Permanent creature = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        return permanent != null
                && creature != null
                && permanent.damage(creature.getPower().getValue(), creature.getId(), source, game) > 0;
    }

    @Override
    public GarrukRelentlessDamageEffect copy() {
        return new GarrukRelentlessDamageEffect(this);
    }
}
