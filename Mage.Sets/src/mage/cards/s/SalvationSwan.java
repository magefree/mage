package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlWithCounterTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SalvationSwan extends CardImpl {

    private static final FilterControlledPermanent filterBird = new FilterControlledPermanent(SubType.BIRD, "Bird you control");
    private static final FilterControlledCreaturePermanent filterWithoutFlying = new FilterControlledCreaturePermanent("creature you control without flying");

    static {
        filterWithoutFlying.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public SalvationSwan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Salvation Swan or another Bird you control enters, exile up to one target creature you control without flying. Return it to the battlefield under its owner's control with a flying counter on it at the beginning of the next end step.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new SalvationSwanTargetEffect(), filterBird, false, true
        );
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1, filterWithoutFlying, false));
        this.addAbility(ability);
    }

    private SalvationSwan(final SalvationSwan card) {
        super(card);
    }

    @Override
    public SalvationSwan copy() {
        return new SalvationSwan(this);
    }
}

// Similar to Otherwordly Journey
class SalvationSwanTargetEffect extends OneShotEffect {

    SalvationSwanTargetEffect() {
        super(Outcome.Benefit);
        staticText = " exile up to one target creature you control without flying. "
                + "Return it to the battlefield under its owner's control "
                + "with a flying counter on it at the beginning of the next end step";
    }

    private SalvationSwanTargetEffect(final SalvationSwanTargetEffect effect) {
        super(effect);
    }

    @Override
    public SalvationSwanTargetEffect copy() {
        return new SalvationSwanTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        // We want a unique exile zone for each time this effect resolves.
        UUID exileId = UUID.randomUUID();
        String exileName = CardUtil.getSourceIdName(game, source);
        permanent.moveToExile(exileId, exileName, source, game);

        OneShotEffect effect = new ReturnToBattlefieldUnderOwnerControlWithCounterTargetEffect(
                CounterType.FLYING.createInstance(), true
        );

        ExileZone exile = game.getExile().getExileZone(exileId);
        if (exile != null) {
            exile.setCleanupOnEndTurn(true);
            effect.setTargetPointer(new FixedTargets(exile.getCards(game), game));
        }

        // create delayed triggered ability, of note the trigger is created even if no card would be returned.
        // TODO: There is currently no way to know which cards will be returned by the trigger.
        //       Maybe we need a hint to refer to the content of an exile zone?
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
        return true;
    }
}