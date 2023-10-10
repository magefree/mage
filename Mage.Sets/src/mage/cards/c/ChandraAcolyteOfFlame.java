package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.MayCastTargetThenExileEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.RedElementalToken;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801, xenohedron
 */
public final class ChandraAcolyteOfFlame extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPlaneswalkerPermanent("red planeswalker you control");
    private static final FilterCard filter2
            = new FilterInstantOrSorceryCard("instant or sorcery card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
        filter2.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public ChandraAcolyteOfFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);
        this.setStartingLoyalty(4);

        // 0: Put a loyalty counter on each red planeswalker you control.
        this.addAbility(new LoyaltyAbility(new AddCountersAllEffect(CounterType.LOYALTY.createInstance(), filter), 0));

        // 0: Create two 1/1 red Elemental creature tokens. They gain haste. Sacrifice them at the beginning of the next end step.
        this.addAbility(new LoyaltyAbility(new ChandraAcolyteOfFlameEffect(), 0));

        // -2: You may cast target instant or sorcery card with converted mana cost 3 or less from your graveyard. If that card would be put into your graveyard this turn, exile it instead.
        Ability ability = new LoyaltyAbility(new MayCastTargetThenExileEffect(false), -2);
        ability.addTarget(new TargetCardInYourGraveyard(filter2));
        this.addAbility(ability);
    }

    private ChandraAcolyteOfFlame(final ChandraAcolyteOfFlame card) {
        super(card);
    }

    @Override
    public ChandraAcolyteOfFlame copy() {
        return new ChandraAcolyteOfFlame(this);
    }
}

class ChandraAcolyteOfFlameEffect extends OneShotEffect {

    ChandraAcolyteOfFlameEffect() {
        super(Outcome.Benefit);
        staticText = "Create two 1/1 red Elemental creature tokens. They gain haste. " +
                "Sacrifice them at the beginning of the next end step.";
    }

    private ChandraAcolyteOfFlameEffect(final ChandraAcolyteOfFlameEffect effect) {
        super(effect);
    }

    @Override
    public ChandraAcolyteOfFlameEffect copy() {
        return new ChandraAcolyteOfFlameEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new RedElementalToken();
        token.putOntoBattlefield(2, game, source, source.getControllerId());

        token.getLastAddedTokenIds().stream().forEach(permId -> {
            Permanent permanent = game.getPermanent(permId);
            if (permanent == null) {
                return;
            }

            ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.Custom);
            effect.setTargetPointer(new FixedTarget(permId, game));
            game.addEffect(effect, source);

            Effect effect2 = new SacrificeTargetEffect();
            effect2.setTargetPointer(new FixedTarget(permId, game));
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect2), source);

            // extra info
            InfoEffect.addInfoToPermanent(game, source, permanent, "<i><b>Warning</b>: It will be sacrificed at the beginning of the next end step</i>");
        });

        return true;
    }
}
