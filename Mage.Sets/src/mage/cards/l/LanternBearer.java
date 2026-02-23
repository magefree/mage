package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LanternBearer extends TransformingDoubleFacedCard {

    public LanternBearer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT}, "{U}",
                "Lanterns' Lift",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA}, "U"
        );

        // Lantern Bearer
        this.getLeftHalfCard().setPT(1, 1);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // Lanterns' Lift
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getRightHalfCard().getSpellAbility().addTarget(auraTarget);
        this.getRightHalfCard().getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.getRightHalfCard().addAbility(new EnchantAbility(auraTarget));

        // Disturb {2}{U}
        // needs to be added after right half has spell ability target set
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{2}{U}"));

        // Enchanted creature gets +1/+1 and has flying.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(
                1, 1, Duration.WhileOnBattlefield
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.AURA
        ).setText("and has flying"));
        this.getRightHalfCard().addAbility(ability);

        // If Lanterns' Lift would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeBackAbility());
    }

    private LanternBearer(final LanternBearer card) {
        super(card);
    }

    @Override
    public LanternBearer copy() {
        return new LanternBearer(this);
    }
}
