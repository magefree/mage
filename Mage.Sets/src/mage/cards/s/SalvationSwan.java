package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlWithCounterTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
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

    static final String VALUE_PREFIX = "SalvationSwanExile";

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
        MageObject sourceObject = source.getSourceObject(game);
        if (permanent == null || sourceObject == null) {
            return false;
        }

        // exile card
        // use workaround with temp zone to get all moved objects like melded parts
        ExileZone tempExileZone = game.getExile().createZone(UUID.randomUUID(), "temp");
        permanent.moveToExile(tempExileZone.getId(), tempExileZone.getName(), source, game);
        // TODO: delete temp zone somehow?

        // return it on next end turn
        Cards cardsToReturn = new CardsImpl(tempExileZone.getCards(game));
        cardsToReturn.retainZone(Zone.EXILED, game);
        Effect effect = new ReturnToBattlefieldUnderOwnerControlWithCounterTargetEffect(
                CounterType.FLYING.createInstance(), true
        );
        effect.setTargetPointer(new FixedTargets(cardsToReturn.getCards(game), game));
        // create delayed triggered ability, of note the trigger is created even if no card would be returned.
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);

        // move exiled cards to shared zone for better UX
        String exileKey = SalvationSwan.VALUE_PREFIX + "_" + source.getSourceId() + "_" + source.getSourceObjectZoneChangeCounter();
        ExileZone sharedExileZone = game.getExile().createZone(
                CardUtil.getExileZoneId(exileKey, game),
                sourceObject.getIdName()
        );
        sharedExileZone.setCleanupOnEndTurn(true);
        tempExileZone.getCards(game).forEach(card -> {
            game.getState().getExile().moveToAnotherZone(card, game, sharedExileZone);
        });

        return true;
    }
}