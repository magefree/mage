package mage.cards.r;

import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TieredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RestorationMagic extends CardImpl {

    public RestorationMagic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Tiered
        this.addAbility(new TieredAbility(this));

        // * Cure -- {0} -- Target permanent gains hexproof and indestructible until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance())
                .setText("target permanent gains hexproof"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                .setText("and indestructible until end of turn"));
        this.getSpellAbility().addTarget(new TargetPermanent());
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(0));
        this.getSpellAbility().withFirstModeFlavorWord("Cure");

        // * Cura -- {1} -- Target permanent gains hexproof and indestructible until end of turn. You gain 3 life.
        this.getSpellAbility().addMode(new Mode(new GainAbilityTargetEffect(HexproofAbility.getInstance())
                .setText("target permanent gains hexproof"))
                .addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                        .setText("and indestructible until end of turn"))
                .addEffect(new GainLifeEffect(3))
                .addTarget(new TargetPermanent())
                .withCost(new GenericManaCost(1)).withFlavorWord("Cura"));

        // * Curaga -- {3}{W} -- Permanents you control gain hexproof and indestructible until end of turn. You gain 6 life.
        this.getSpellAbility().addMode(new Mode(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT
        ).setText("permanents you control gain hexproof"))
                .addEffect(new GainAbilityControlledEffect(
                        IndestructibleAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT
                ).setText("and indestructible until end of turn"))
                .addEffect(new GainLifeEffect(6))
                .withCost(new ManaCostsImpl<>("{3}{W}"))
                .withFlavorWord("Curaga"));
    }

    private RestorationMagic(final RestorationMagic card) {
        super(card);
    }

    @Override
    public RestorationMagic copy() {
        return new RestorationMagic(this);
    }
}
