package mage.cards.t;

import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.TieredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThunderMagic extends CardImpl {

    public ThunderMagic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Tiered
        this.addAbility(new TieredAbility(this));

        // * Thunder -- {0} -- Thunder Magic deals 2 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().withFirstModeFlavorWord("Thunder");
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(0));

        // * Thundara -- {3} -- Thunder Magic deals 4 damage to target creature.
        this.getSpellAbility().addMode(new Mode(new DamageTargetEffect(4))
                .addTarget(new TargetCreaturePermanent())
                .withFlavorWord("Thundara")
                .withCost(new GenericManaCost(3)));

        // * Thundaga -- {5}{R} -- Thunder Magic deals 8 damage to target creature.
        this.getSpellAbility().addMode(new Mode(new DamageTargetEffect(8))
                .addTarget(new TargetCreaturePermanent())
                .withFlavorWord("Thundaga")
                .withCost(new ManaCostsImpl<>("{5}{R}")));
    }

    private ThunderMagic(final ThunderMagic card) {
        super(card);
    }

    @Override
    public ThunderMagic copy() {
        return new ThunderMagic(this);
    }
}
