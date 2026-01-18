package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.hint.common.ModesAlreadyUsedHint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HenrikaDomnathi extends TransformingDoubleFacedCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each creature you control with flying, deathtouch, and/or lifelink");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(Predicates.or(
                new AbilityPredicate(FlyingAbility.class),
                new AbilityPredicate(DeathtouchAbility.class),
                new AbilityPredicate(LifelinkAbility.class)
        ));
    }

    public HenrikaDomnathi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.VAMPIRE}, "{2}{B}{B}",
                "Henrika, Infernal Seer",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.VAMPIRE}, "B"
        );

        // Henrika Domnathi
        this.getLeftHalfCard().setPT(1, 3);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // At the beginning of combat on your turn, choose one that hasn't been chosen —
        // • Each player sacrifices a creature.
        Ability ability = new BeginningOfCombatTriggeredAbility(new SacrificeAllEffect(StaticFilters.FILTER_PERMANENT_CREATURE));
        ability.setModeTag("each player sacrifice");
        ability.getModes().setLimitUsageByOnce(false);

        // • You draw a card and you lose 1 life.
        Mode mode = new Mode(new DrawCardSourceControllerEffect(1));
        mode.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        mode.setModeTag("draw and lose life");
        ability.addMode(mode);

        // • Transform Henrika Domnathi.
        ability.addMode(new Mode(new TransformSourceEffect()).setModeTag("transform"));

        ability.addHint(ModesAlreadyUsedHint.instance);
        this.getLeftHalfCard().addAbility(ability);

        // Henrika, Infernal Seer
        this.getRightHalfCard().setPT(3, 4);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.getRightHalfCard().addAbility(DeathtouchAbility.getInstance());

        // Lifelink
        this.getRightHalfCard().addAbility(LifelinkAbility.getInstance());

        // {1}{B}{B}: Each creature you control with flying, deathtouch, and/or lifelink gets +1/+0 until end of turn.
        this.getRightHalfCard().addAbility(new SimpleActivatedAbility(new BoostAllEffect(
                1, 0, Duration.EndOfTurn, filter, false
        ), new ManaCostsImpl<>("{1}{B}{B}")));
    }

    private HenrikaDomnathi(final HenrikaDomnathi card) {
        super(card);
    }

    @Override
    public HenrikaDomnathi copy() {
        return new HenrikaDomnathi(this);
    }
}
