package mage.cards.b;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class BindTheMonster extends CardImpl {

    public BindTheMonster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Bind the Monster enters the battlefield, tap enchanted creature. It deals damage to you equal to its power.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BindTheMonsterEffect()));

        // Enchanted creature doesn't untap during its controller's untap step.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepEnchantedEffect()));
    }

    private BindTheMonster(final BindTheMonster card) {
        super(card);
    }

    @Override
    public BindTheMonster copy() {
        return new BindTheMonster(this);
    }
}

class BindTheMonsterEffect extends OneShotEffect {

    public BindTheMonsterEffect() {
        super(Outcome.Tap);
        staticText = "tap enchanted creature. It deals damage to you equal to its power";
    }

    private BindTheMonsterEffect(final BindTheMonsterEffect effect) {
        super(effect);
    }

    @Override
    public BindTheMonsterEffect copy() {
        return new BindTheMonsterEffect(this);
    }

    @Override
    public boolean apply (Game game, Ability source) {
        Permanent attachment = source.getSourcePermanentIfItStillExists(game);
        if (attachment != null) {
            Permanent creature = game.getPermanent(attachment.getAttachedTo());
            if (creature != null) {
                creature.tap(source, game);
                Player player = game.getPlayer(source.getControllerId());
                if (player != null) {
                    player.damage(creature.getPower().getValue(), creature.getId(), source, game);
                }
                return true;
            }
        }
        return false;
    }
}
