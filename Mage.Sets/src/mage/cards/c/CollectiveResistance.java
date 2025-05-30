package mage.cards.c;

import mage.abilities.Mode;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.EscalateAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CollectiveResistance extends CardImpl {

    public CollectiveResistance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Escalate {G}
        this.addAbility(new EscalateAbility(new ManaCostsImpl<>("{G}")));

        // Choose one or more --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(3);

        // * Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());

        // * Destroy target enchantment.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetEnchantmentPermanent()));

        // * Target creature gains hexproof and indestructible until end of turn.
        this.getSpellAbility().addMode(new Mode(
                new GainAbilityTargetEffect(HexproofAbility.getInstance())
                        .setText("target creature gains hexproof"))
                .addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                        .setText("and indestructible until end of turn"))
                .addTarget(new TargetCreaturePermanent()));
    }

    private CollectiveResistance(final CollectiveResistance card) {
        super(card);
    }

    @Override
    public CollectiveResistance copy() {
        return new CollectiveResistance(this);
    }
}
