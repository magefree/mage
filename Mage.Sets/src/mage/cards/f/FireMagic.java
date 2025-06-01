package mage.cards.f;

import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.TieredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FireMagic extends CardImpl {

    public FireMagic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Tiered
        this.addAbility(new TieredAbility(this));

        // * Fire -- {0} -- Fire Magic deals 1 damage to each creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(1, StaticFilters.FILTER_PERMANENT_CREATURE));
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(0));
        this.getSpellAbility().withFirstModeFlavorWord("Fire");

        // * Fira -- {2} -- Fire Magic deals 2 damage to each creature.
        this.getSpellAbility().addMode(new Mode(new DamageAllEffect(2, StaticFilters.FILTER_PERMANENT_CREATURE))
                .withCost(new GenericManaCost(2)).withFlavorWord("Fira"));

        // * Firaga -- {5} -- Fire Magic deals 3 damage to each creature.
        this.getSpellAbility().addMode(new Mode(new DamageAllEffect(3, StaticFilters.FILTER_PERMANENT_CREATURE))
                .withCost(new GenericManaCost(5)).withFlavorWord("Firaga"));
    }

    private FireMagic(final FireMagic card) {
        super(card);
    }

    @Override
    public FireMagic copy() {
        return new FireMagic(this);
    }
}
