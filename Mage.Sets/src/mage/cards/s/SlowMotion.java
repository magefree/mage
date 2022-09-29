
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class SlowMotion extends CardImpl {

    public SlowMotion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // At the beginning of the upkeep of enchanted creature's controller, that player sacrifices that creature unless they pay {2}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeEquipedUnlessPaysEffect(new GenericManaCost(2)), TargetController.CONTROLLER_ATTACHED_TO, false));

        // When Slow Motion is put into a graveyard from the battlefield, return Slow Motion to its owner's hand.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new ReturnToHandSourceEffect()));
    }

    private SlowMotion(final SlowMotion card) {
        super(card);
    }

    @Override
    public SlowMotion copy() {
        return new SlowMotion(this);
    }
}

class SacrificeEquipedUnlessPaysEffect extends OneShotEffect {

    protected Cost cost;

    public SacrificeEquipedUnlessPaysEffect(Cost cost) {
        super(Outcome.Sacrifice);
        this.cost = cost;
        staticText = "that player sacrifices that creature unless they pay {2}";
    }

    public SacrificeEquipedUnlessPaysEffect(final SacrificeEquipedUnlessPaysEffect effect) {
        super(effect);
        this.cost = effect.cost.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment == null) {
            return false;
        }
        Permanent equipped = game.getPermanent(equipment.getAttachedTo());
        if (equipped == null) {
            return false;
        }
        Player player = game.getPlayer(equipped.getControllerId());
        if (player == null) {
            return false;
        }
        if (player.chooseUse(Outcome.Benefit, "Pay " + cost.getText() + "? (Or " + equipped.getName() + " will be sacrificed.)", source, game)) {
            cost.clearPaid();
            if (cost.pay(source, game, source, equipped.getControllerId(), false, null)) {
                return true;
            }
        }
        equipped.sacrifice(source, game);
        return true;
    }

    @Override
    public SacrificeEquipedUnlessPaysEffect copy() {
        return new SacrificeEquipedUnlessPaysEffect(this);
    }
}
