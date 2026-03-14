package mage.cards.k;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KindlyAncestor extends TransformingDoubleFacedCard {

    public KindlyAncestor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT}, "{2}{W}",
                "Ancestor's Embrace",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA}, "W");

        // Kindly Ancestor
        this.getLeftHalfCard().setPT(2, 3);

        // Lifelink
        this.getLeftHalfCard().addAbility(LifelinkAbility.getInstance());

        // Ancestor's Embrace
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getRightHalfCard().getSpellAbility().addTarget(auraTarget);
        this.getRightHalfCard().getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.getRightHalfCard().addAbility(new EnchantAbility(auraTarget));

        // Disturb {1}{W}
        // needs to be added after right half has spell ability target set
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{1}{W}"));

        // Enchanted creature has lifelink.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                LifelinkAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield
        )));

        // If Ancestor's Embrace would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeBackAbility());
    }

    private KindlyAncestor(final KindlyAncestor card) {
        super(card);
    }

    @Override
    public KindlyAncestor copy() {
        return new KindlyAncestor(this);
    }
}
