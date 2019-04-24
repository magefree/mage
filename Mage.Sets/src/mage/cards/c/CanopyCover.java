
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CantBeTargetedAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterObject;
import mage.filter.FilterStackObject;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author noxx
 */
public final class CanopyCover extends CardImpl {

    private static final FilterObject filter = new FilterStackObject("spells or abilities your opponents control");

    public CanopyCover(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature can't be blocked except by creatures with flying or reach. (!this is a static ability of the enchantment)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CanopyCoverEffect()));

        // Enchanted creature can't be the target of spells or abilities your opponents control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeTargetedAttachedEffect(filter, Duration.WhileOnBattlefield, AttachmentType.AURA, TargetController.OPPONENT)));
    }

    public CanopyCover(final CanopyCover card) {
        super(card);
    }

    @Override
    public CanopyCover copy() {
        return new CanopyCover(this);
    }
}

class CanopyCoverEffect extends RestrictionEffect {

    public CanopyCoverEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Enchanted creature can't be blocked except by creatures with flying or reach";
    }

    public CanopyCoverEffect(final CanopyCoverEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent equipped = game.getPermanent(equipment.getAttachedTo());
            if (equipped != null && permanent.getId().equals(equipped.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return blocker.getAbilities().contains(FlyingAbility.getInstance()) || blocker.getAbilities().contains(ReachAbility.getInstance());
    }

    @Override
    public CanopyCoverEffect copy() {
        return new CanopyCoverEffect(this);
    }
}
