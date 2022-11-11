package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBlockAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class IronclawCurse extends CardImpl {

    public IronclawCurse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets 0/-1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(0, -1, Duration.WhileOnBattlefield)));

        // Enchanted creature can't block creatures with power equal to or greater than the enchanted creature's toughness.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new IronclawCurseEffect()));
    }

    private IronclawCurse(final IronclawCurse card) {
        super(card);
    }

    @Override
    public IronclawCurse copy() {
        return new IronclawCurse(this);
    }
}

class IronclawCurseEffect extends CantBlockAttachedEffect {

    public IronclawCurseEffect() {
        super(AttachmentType.AURA);
        this.staticText = "Enchanted creature can't block creatures with power equal to or greater than the enchanted creature's toughness";
    }

    public IronclawCurseEffect(final IronclawCurseEffect effect) {
        super(effect);
    }

    @Override
    public IronclawCurseEffect copy() {
        return new IronclawCurseEffect(this);
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        if (attacker == null) {
            return true;
        }
        // In the case that the enchantment is blinked
        Permanent enchantment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if (enchantment == null) {
            // It was not blinked, use the standard method
            enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        }
        if (enchantment == null) {
            return false;
        }
        Permanent enchantedCreature = game.getPermanent(enchantment.getAttachedTo());
        if (enchantment.getAttachedTo() != null) {
            return !(attacker.getPower().getValue() >= enchantedCreature.getToughness().getValue());
        }
        return true;
    }
}
