package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAttachedEffect;
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
 * @author Styxo
 */
public final class CloakingDevice extends CardImpl {

    public CloakingDevice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature can't be blocked.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedAttachedEffect(AttachmentType.AURA)));

        // Whenever enchanted creature attacks, defending player loses 1 life.
        this.addAbility(new AttacksAttachedTriggeredAbility(new CloakingDeviceLoseLifeDefendingPlayerEffect(), AttachmentType.AURA, false));

    }

    private CloakingDevice(final CloakingDevice card) {
        super(card);
    }

    @Override
    public CloakingDevice copy() {
        return new CloakingDevice(this);
    }
}

class CloakingDeviceLoseLifeDefendingPlayerEffect extends OneShotEffect {

    public CloakingDeviceLoseLifeDefendingPlayerEffect() {
        super(Outcome.Damage);
        this.staticText = "defending player loses 1 life";
    }

    public CloakingDeviceLoseLifeDefendingPlayerEffect(final CloakingDeviceLoseLifeDefendingPlayerEffect effect) {
        super(effect);
    }

    @Override
    public CloakingDeviceLoseLifeDefendingPlayerEffect copy() {
        return new CloakingDeviceLoseLifeDefendingPlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // In the case that the enchantment is blinked
        Permanent enchantment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if (enchantment == null) {
            // It was not blinked, use the standard method
            enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        }
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Player defender = game.getPlayer(game.getCombat().getDefendingPlayerId(enchantment.getAttachedTo(), game));
            if (defender != null) {
                defender.loseLife(1, game, source, false);
            }
        }
        return true;
    }
}
