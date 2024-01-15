package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jimga150
 */
public final class SwoopingPteranodon extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.DINOSAUR, "Dinosaur with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public SwoopingPteranodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");
        
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Swooping Pteranodon or another Dinosaur with flying enters the battlefield under your control,
        // gain control of target creature an opponent controls until end of turn. Untap that creature.
        // It gains flying and haste until end of turn.
        // At the beginning of the next end step, target land deals 3 damage to that creature.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new GainControlTargetEffect(Duration.EndOfTurn), filter, true, true);
        ability.addEffect(new UntapTargetEffect().setText("Untap that creature"));
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn).setText("It gains haste until end of turn."));
        ability.addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn).setText("It gains flying until end of turn."));

        TargetPermanent stolenCreatureTarget = new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE);
        ability.addTarget(stolenCreatureTarget);

        this.addAbility(ability);

        Effect damageEffect = new DamageFromOneToAnotherTargetEffect(3);
        damageEffect.setText("target land deals 3 damage to that creature.");

        Ability ability2 = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(damageEffect);
        ability2.addTarget(new TargetPermanent(StaticFilters.FILTER_LAND));
        ability2.addTarget(stolenCreatureTarget);

        this.addAbility(ability2);

        // Swooping Pteranodon's triggered ability targets only the creature an opponent controls. You choose the
        // target land (where the poor creature is going to drop) as the delayed triggered ability triggers at the
        // beginning of the next end step.
        // (2023-11-10)
        // The little "(where the poor creature is going to drop)" is like, funny, but mainly it also drives home
        // the love and care that this game gets. The official rulings making a joke about how the weird specifics of
        // the mechanics of a card play into its flavor is a nice little touch, to me.
    }

    private SwoopingPteranodon(final SwoopingPteranodon card) {
        super(card);
    }

    @Override
    public SwoopingPteranodon copy() {
        return new SwoopingPteranodon(this);
    }
}
