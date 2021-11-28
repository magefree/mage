package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Jason E. Wall
 */
public final class TreetopBracers extends CardImpl {

    public TreetopBracers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 and can't be blocked except by creatures with flying.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, 1, Duration.WhileOnBattlefield));
        ability.addEffect(new TreetopBracersRestrictEffect());
        this.addAbility(ability);
    }

    private TreetopBracers(final TreetopBracers card) {
        super(card);
    }

    @Override
    public TreetopBracers copy() {
        return new TreetopBracers(this);
    }
}

class TreetopBracersRestrictEffect extends RestrictionEffect {

    public TreetopBracersRestrictEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "and can't be blocked except by creatures with flying";
    }

    public TreetopBracersRestrictEffect(final TreetopBracersRestrictEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null
                && equipment.getAttachedTo() != null) {
            Permanent equipped = game.getPermanent(equipment.getAttachedTo());
            return equipped != null
                    && permanent != null
                    && permanent.getId().equals(equipped.getId());
        }
        return false;
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return blocker.getAbilities().contains(FlyingAbility.getInstance()) || blocker.getAbilities().contains(ReachAbility.getInstance());
    }

    @Override
    public TreetopBracersRestrictEffect copy() {
        return new TreetopBracersRestrictEffect(this);
    }
}
