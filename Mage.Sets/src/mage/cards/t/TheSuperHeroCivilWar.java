package mage.cards.t;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.condition.common.SourceRemainsInZoneCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author werhsdnas
 */
public final class TheSuperHeroCivilWar extends CardImpl {

    public TheSuperHeroCivilWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{W}");
        
        this.subtype.add(SubType.SAGA);

        SagaAbility sagaAbility = new SagaAbility(this);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        // I -- Gain control of up to two target creatures with total mana value 6 or less for as long as this Saga remains on the battlefield.
        sagaAbility.addChapterEffect(
                this,
                SagaChapter.CHAPTER_I,
                new ConditionalContinuousEffect(
                        new GainControlTargetEffect(Duration.Custom, true),
                        new SourceRemainsInZoneCondition(Zone.BATTLEFIELD),
                        ""
                ).setText("Gain control of up to two target creatures with total mana value 6 or less for as long as this Saga remains on the battlefield."), new TheSuperHeroCivilWarTarget()
        );
        // II -- Creatures you control get +1/+1 and gain vigilance until end of turn.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II,
                new BoostControlledEffect(1, 1, Duration.EndOfTurn)
                        .setText("creatures you control get +1/+1"),
                new GainAbilityControlledEffect(
                        VigilanceAbility.getInstance(), Duration.EndOfTurn,
                        StaticFilters.FILTER_PERMANENT_CREATURE
                ).setText("and gain vigilance until end of turn")
        );
        // III -- Target creature you control fights up to one other target creature.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                ability -> {
                    ability.addEffect(new FightTargetsEffect().setText(
                            "Target creature you control fights up to one other target creature."
                    ));
                    ability.addTarget(new TargetControlledCreaturePermanent().setTargetTag(1));
                    ability.addTarget(new TargetPermanent(
                            0, 1, StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2
                    ).setTargetTag(2));
                }
        );
        this.addAbility(sagaAbility);
    }

    private TheSuperHeroCivilWar(final TheSuperHeroCivilWar card) {
        super(card);
    }

    @Override
    public TheSuperHeroCivilWar copy() {
        return new TheSuperHeroCivilWar(this);
    }
}

class TheSuperHeroCivilWarTarget extends TargetCreaturePermanent {
    TheSuperHeroCivilWarTarget() {
        super(0, 2);
    }

    private TheSuperHeroCivilWarTarget(final TheSuperHeroCivilWarTarget target) {
        super(target);
    }

    @Override
    public TheSuperHeroCivilWarTarget copy() {
        return new TheSuperHeroCivilWarTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return super.canTarget(playerId, id, source, game)
                && CardUtil.checkCanTargetTotalValueLimit(
                this.getTargets(), id, MageObject::getManaValue, 6, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        return CardUtil.checkPossibleTargetsTotalValueLimit(this.getTargets(),
                super.possibleTargets(sourceControllerId, source, game),
                MageObject::getManaValue, 6, game);
    }

    @Override
    public String getMessage(Game game) {
        // shows selected total
        int selectedValue = this.getTargets().stream()
                .map(game::getObject)
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum();
        return super.getMessage(game) + " (selected total mana value " + selectedValue + ")";
    }
}
