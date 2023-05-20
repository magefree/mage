package mage.cards.d;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DrogskolInfantry extends TransformingDoubleFacedCard {

    public DrogskolInfantry(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT, SubType.SOLDIER}, "{1}{W}",
                "Drogskol Armaments",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA}, "W"
        );
        this.getLeftHalfCard().setPT(2, 2);

        // Disturb {3}{W}
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{3}{W}"));

        // Drogskol Armaments
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getRightHalfCard().getSpellAbility().addTarget(auraTarget);
        this.getRightHalfCard().getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.getRightHalfCard().addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +2/+2.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(2, 2)));

        // If Drogskol Armaments would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeExileAbility());
    }

    private DrogskolInfantry(final DrogskolInfantry card) {
        super(card);
    }

    @Override
    public DrogskolInfantry copy() {
        return new DrogskolInfantry(this);
    }
}
