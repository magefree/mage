package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodthirstyBlade extends CardImpl {

    public BloodthirstyBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0 and is goaded.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 0));
        ability.addEffect(new AttacksIfAbleAttachedEffect(
                Duration.WhileOnBattlefield, AttachmentType.EQUIPMENT
        ).setText("and is"));
        ability.addEffect(new BloodthirstyBladeAttackEffect());
        this.addAbility(ability);

        // {1}: Attach Bloodthirsty Blade to target creature an opponent controls. Active this ability only any time you could cast a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD,
                new AttachEffect(
                        Outcome.Benefit, "Attach {this} to target creature an opponent controls"
                ), new GenericManaCost(1)
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private BloodthirstyBlade(final BloodthirstyBlade card) {
        super(card);
    }

    @Override
    public BloodthirstyBlade copy() {
        return new BloodthirstyBlade(this);
    }
}

class BloodthirstyBladeAttackEffect extends RestrictionEffect {

    BloodthirstyBladeAttackEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "goaded";
    }

    private BloodthirstyBladeAttackEffect(final BloodthirstyBladeAttackEffect effect) {
        super(effect);
    }

    @Override
    public BloodthirstyBladeAttackEffect copy() {
        return new BloodthirstyBladeAttackEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        return attachment != null && attachment.getAttachedTo() != null
                && permanent.getId().equals(attachment.getAttachedTo());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null) {
            return true;
        }
        return !defenderId.equals(source.getControllerId());
    }
}
