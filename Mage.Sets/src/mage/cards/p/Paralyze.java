
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.*;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author dustinconrad
 */
public final class Paralyze extends CardImpl {

    public Paralyze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));
        // When Paralyze enters the battlefield, tap enchanted creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TapEnchantedEffect()));
        // Enchanted creature doesn't untap during its controller's untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepEnchantedEffect()));
        // At the beginning of the upkeep of enchanted creature's controller, that player may pay {4}. If they do, untap the creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new ParalyzeEffect(), TargetController.CONTROLLER_ATTACHED_TO, false));
    }

    private Paralyze(final Paralyze card) {
        super(card);
    }

    @Override
    public Paralyze copy() {
        return new Paralyze(this);
    }
}

class ParalyzeEffect extends DoIfCostPaid {

    public ParalyzeEffect() {
        super(new UntapEnchantedEffect(), new GenericManaCost(4));
    }

    public ParalyzeEffect(final ParalyzeEffect effect) {
        super(effect);
    }

    @Override
    public ParalyzeEffect copy() {
        return new ParalyzeEffect(this);
    }

    @Override
    protected Player getPayingPlayer(Game game, Ability source) {
        Permanent attachment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (attachment != null && attachment.getAttachedTo() != null) {
            Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
            if (attachedTo != null) {
                return game.getPlayer(attachedTo.getControllerId());
            }
        }
        return null;
    }

    @Override
    public String getText(Mode mode) {
        return "that player may " + CardUtil.addCostVerb(cost.getText()) + ". If they do, " + executingEffects.getText(mode);
    }
}
