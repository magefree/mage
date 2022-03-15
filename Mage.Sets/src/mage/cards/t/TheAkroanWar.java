package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;
import mage.abilities.condition.common.SourceRemainsInZoneCondition;

/**
 * @author TheElk801
 */
public final class TheAkroanWar extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterOpponentsCreaturePermanent("creatures your opponents control");

    public TheAkroanWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I — Gain control of target creature for as long as The Akroan War remains on the battlefield.
        sagaAbility.addChapterEffect(
                this,
                SagaChapter.CHAPTER_I,
                SagaChapter.CHAPTER_I,
                new ConditionalContinuousEffect(
                        new GainControlTargetEffect(Duration.Custom, true),
                        new SourceRemainsInZoneCondition(Zone.BATTLEFIELD),
                        "gain control of target creature for as long as {this} remains on the battlefield"
                ), new TargetCreaturePermanent()
        );

        // II — Until your next turn, creatures your opponents control attack each combat if able.
        sagaAbility.addChapterEffect(
                this,
                SagaChapter.CHAPTER_II,
                new AttacksIfAbleAllEffect(
                        filter, Duration.UntilYourNextTurn, true
                ).setText("until your next turn, creatures your opponents control attack each combat if able")
        );

        // III — Each tapped creature deals damage to itself equal to its power.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new TheAkroanWarEffect());
        this.addAbility(sagaAbility);
    }

    private TheAkroanWar(final TheAkroanWar card) {
        super(card);
    }

    @Override
    public TheAkroanWar copy() {
        return new TheAkroanWar(this);
    }
}

class TheAkroanWarEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    TheAkroanWarEffect() {
        super(Outcome.Benefit);
        staticText = "each tapped creature deals damage to itself equal to its power";
    }

    private TheAkroanWarEffect(final TheAkroanWarEffect effect) {
        super(effect);
    }

    @Override
    public TheAkroanWarEffect copy() {
        return new TheAkroanWarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getBattlefield()
                .getActivePermanents(filter, source.getControllerId(), game)
                .stream()
                .forEach(permanent -> permanent.damage(permanent.getPower().getValue(), permanent.getId(), source, game));
        return true;
    }
}