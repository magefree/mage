package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBlockAttachedEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class VisionsOfBrutality extends CardImpl {

    public VisionsOfBrutality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.subtype.add(SubType.AURA);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature can't block.
        this.addAbility(new SimpleStaticAbility(new CantBlockAttachedEffect(AttachmentType.AURA)));

        // Whenever enchanted creature deals damage, its controller loses that much life.
        this.addAbility(new DealsDamageAttachedTriggeredAbility(Zone.BATTLEFIELD, new VisionsOfBrutalityEffect(), false));
    }

    private VisionsOfBrutality(final VisionsOfBrutality card) {
        super(card);
    }

    @Override
    public VisionsOfBrutality copy() {
        return new VisionsOfBrutality(this);
    }
}

class VisionsOfBrutalityEffect extends OneShotEffect {

    VisionsOfBrutalityEffect() {
        super(Outcome.Benefit);
        this.staticText = "its controller loses that much life";
    }

    private VisionsOfBrutalityEffect(final VisionsOfBrutalityEffect effect) {
        super(effect);
    }

    @Override
    public VisionsOfBrutalityEffect copy() {
        return new VisionsOfBrutalityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent enchantment = source.getSourcePermanentOrLKI(game);
        if (controller == null || enchantment == null || enchantment.getAttachedTo() == null) {
            return false;
        }
        Permanent enchanted = game.getPermanentOrLKIBattlefield(enchantment.getAttachedTo());
        if (enchanted != null) {
            Player controllerEnchanted = game.getPlayer(enchanted.getControllerId());
            if (controllerEnchanted != null) {
                int damage = (Integer) getValue("damage");
                if (damage > 0) {
                    controllerEnchanted.loseLife(damage, game, source, false);
                }
            }
        }
        return true;
    }
}
