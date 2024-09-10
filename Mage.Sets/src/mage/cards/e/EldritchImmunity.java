package mage.cards.e;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EldritchImmunity extends CardImpl {

    private static final FilterCard filter = new FilterCard("each color");

    static {
        filter.add(Predicates.not(ColorlessPredicate.instance));
    }

    public EldritchImmunity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.KINDRED, CardType.INSTANT}, "{C}");

        this.subtype.add(SubType.ELDRAZI);

        // Target creature you control gains protection from each color until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(new ProtectionAbility(filter)));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());

        // Overload {4}{C}
        this.addAbility(new OverloadAbility(
                this,
                new GainAbilityControlledEffect(
                        new ProtectionAbility(filter), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE
                ).setText("each creature you control gains protection from each color until end of turn"),
                new ManaCostsImpl<>("{4}{C}")
        ));
    }

    private EldritchImmunity(final EldritchImmunity card) {
        super(card);
    }

    @Override
    public EldritchImmunity copy() {
        return new EldritchImmunity(this);
    }
}
