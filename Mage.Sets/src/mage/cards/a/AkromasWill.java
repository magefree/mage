package mage.cards.a;

import mage.abilities.Mode;
import mage.abilities.condition.common.ControlACommanderCondition;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AkromasWill extends CardImpl {

    private static final FilterCard filter = new FilterCard("all colors");

    static {
        filter.add(Predicates.not(ColorlessPredicate.instance));
    }

    public AkromasWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Choose one. If you control a commander as you cast this spell, you may choose both.
        this.getSpellAbility().getModes().setChooseText(
                "Choose one. If you control a commander as you cast this spell, you may choose both."
        );
        this.getSpellAbility().getModes().setMoreCondition(ControlACommanderCondition.instance);

        // • Creatures you control gain flying, vigilance, and double strike until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("creatures you control gain flying"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText(", vigilance"));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                DoubleStrikeAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText(", and double strike until end of turn"));

        // • Creatures you control gain lifelink, indestructible, and protection from all colors until end of turn.
        Mode mode = new Mode(new GainAbilityControlledEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("creatures you control gain lifelink"));
        mode.addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText(", indestructible"));
        mode.addEffect(new GainAbilityControlledEffect(
                new ProtectionAbility(filter), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText(", and protection from all colors until end of turn"));
        this.getSpellAbility().addMode(mode);
    }

    private AkromasWill(final AkromasWill card) {
        super(card);
    }

    @Override
    public AkromasWill copy() {
        return new AkromasWill(this);
    }
}
