package mage.cards.t;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TwinbladeGeist extends TransformingDoubleFacedCard {

    public TwinbladeGeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT, SubType.WARRIOR}, "{1}{W}",
                "Twinblade Invocation",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA}, "W"
        );

        // Twinblade Geist
        this.getLeftHalfCard().setPT(1, 1);

        // Double strike
        this.getLeftHalfCard().addAbility(DoubleStrikeAbility.getInstance());

        // Disturb {2}{W}
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{2}{W}"));

        // Twinblade Invocation
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getRightHalfCard().getSpellAbility().addTarget(auraTarget);
        this.getRightHalfCard().getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.getRightHalfCard().addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature has double strike.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                DoubleStrikeAbility.getInstance(), AttachmentType.AURA
        )));

        // If Twinblade Invocation would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeBackAbility());
    }

    private TwinbladeGeist(final TwinbladeGeist card) {
        super(card);
    }

    @Override
    public TwinbladeGeist copy() {
        return new TwinbladeGeist(this);
    }
}
