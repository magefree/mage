package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BindingGeist extends TransformingDoubleFacedCard {

    public BindingGeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT}, "{2}{U}",
                "Spectral Binding",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA}, "U");

        // Binding Geist
        this.getLeftHalfCard().setPT(3, 1);

        // Whenever Binding Geist attacks, target creature an opponent controls gets -2/-0 until end of turn.
        Ability ability = new AttacksTriggeredAbility(new BoostTargetEffect(-2, 0));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.getLeftHalfCard().addAbility(ability);

        // Spectral Binding
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.getRightHalfCard().addAbility(new EnchantAbility(auraTarget));

        // Disturb {1}{U}
        // needs to be added after right half has spell ability target set
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{1}{U}"));

        // Enchanted creature gets -2/-0.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(-2, 0)));

        // If Spectral Binding would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeBackAbility());
    }

    private BindingGeist(final BindingGeist card) {
        super(card);
    }

    @Override
    public BindingGeist copy() {
        return new BindingGeist(this);
    }
}
