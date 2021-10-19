
package mage.cards.c;

import java.util.UUID;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author TheElk801
 */
public final class CustodyBattle extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent();

    public CustodyBattle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature has "At the beginning of your upkeep, target opponent gains control of this creature unless you sacrifice a land."
        ability = new BeginningOfUpkeepTriggeredAbility(new CustodyBattleUnlessPaysEffect(new SacrificeTargetCost(new TargetControlledPermanent(filter))), TargetController.YOU, false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability, AttachmentType.AURA)));

    }

    private CustodyBattle(final CustodyBattle card) {
        super(card);
    }

    @Override
    public CustodyBattle copy() {
        return new CustodyBattle(this);
    }
}

class GiveControlEffect extends ContinuousEffectImpl {

    public GiveControlEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "Target opponent gains control of {this}";
    }

    public GiveControlEffect(final GiveControlEffect effect) {
        super(effect);
    }

    @Override
    public GiveControlEffect copy() {
        return new GiveControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) source.getSourceObjectIfItStillExists(game);
        if (permanent != null) {
            return permanent.changeControllerId(source.getFirstTarget(), game, source);
        } else {
            discard();
        }
        return false;
    }

}

class CustodyBattleUnlessPaysEffect extends OneShotEffect {

    protected Cost cost;

    public CustodyBattleUnlessPaysEffect(Cost cost) {
        super(Outcome.Sacrifice);
        staticText = "target opponent gains control of {this} unless you sacrifice a land";
        this.cost = cost;
    }

    public CustodyBattleUnlessPaysEffect(final CustodyBattleUnlessPaysEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            String message = "sacrifice a land?";
            message = CardUtil.replaceSourceName(message, sourcePermanent.getLogName());
            message = Character.toUpperCase(message.charAt(0)) + message.substring(1);
            if (cost.canPay(source, source, source.getControllerId(), game)
                    && controller.chooseUse(Outcome.Benefit, message, source, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source, source.getControllerId(), false, null)) {
                    return true;
                }
            }
            if (source.getSourceObjectZoneChangeCounter() == game.getState().getZoneChangeCounter(source.getSourceId())
                    && game.getState().getZone(source.getSourceId()) == Zone.BATTLEFIELD) {
                ContinuousEffect effect = new GiveControlEffect();
                effect.setTargetPointer(new FixedTarget(source.getFirstTarget(), game));
                game.addEffect(effect, source);
                game.informPlayers(game.getPlayer(source.getFirstTarget()).getLogName() + " gains control of " + sourcePermanent.getIdName());
            }
            return true;
        }
        return false;
    }

    @Override
    public CustodyBattleUnlessPaysEffect copy() {
        return new CustodyBattleUnlessPaysEffect(this);
    }
}
