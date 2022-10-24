package mage.cards.f;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.ProtectionChosenColorAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author awjackson
 */
public final class FloatingShield extends CardImpl {

    public FloatingShield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Protect));
        this.addAbility(new EnchantAbility(auraTarget));

        // As Floating Shield enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Protect)));

        // Enchanted creature has protection from the chosen color. This effect doesn't remove Floating Shield.
        this.addAbility(new SimpleStaticAbility(new ProtectionChosenColorAttachedEffect(true)));

        // Sacrifice Floating Shield: Target creature gains protection from the chosen color until end of turn.
        Ability ability = new SimpleActivatedAbility(new FloatingShieldEffect(), new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private FloatingShield(final FloatingShield card) {
        super(card);
    }

    @Override
    public FloatingShield copy() {
        return new FloatingShield(this);
    }
}

class FloatingShieldEffect extends OneShotEffect {

    public FloatingShieldEffect() {
        super(Outcome.Protect);
        this.staticText = "target creature gains protection from the chosen color until end of turn";
    }

    public FloatingShieldEffect(final FloatingShieldEffect effect) {
        super(effect);
    }

    @Override
    public FloatingShieldEffect copy() {
        return new FloatingShieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
        if (color == null) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(ProtectionAbility.from(color)), source);
        return true;
    }
}
