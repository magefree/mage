

package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RighteousAuthority extends CardImpl {

    public RighteousAuthority(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{U}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 for each card in its controller's hand.
        CardsInEnchantedControllerHandCount boost = new CardsInEnchantedControllerHandCount();
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(boost, boost, Duration.WhileOnBattlefield)));

        // At the beginning of the draw step of enchanted creature's controller, that player draws an additional card.
        this.addAbility(new BeginningOfDrawTriggeredAbility(new DrawCardTargetEffect(1), TargetController.CONTROLLER_ATTACHED_TO, false));
    }

    private RighteousAuthority(final RighteousAuthority card) {
        super(card);
    }

    @Override
    public RighteousAuthority copy() {
        return new RighteousAuthority(this);
    }
}


class CardsInEnchantedControllerHandCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility != null) {
            Permanent attachment = game.getPermanent(sourceAbility.getSourceId());
            if (attachment != null && attachment.getAttachedTo() != null) {
                Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
                if (attachedTo != null) {
                    Player controller = game.getPlayer(attachedTo.getControllerId());
                    if (controller != null) {
                        return controller.getHand().size();
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public CardsInEnchantedControllerHandCount copy() {
        return new CardsInEnchantedControllerHandCount();
    }

    @Override
    public String getMessage() {
        return "card in its controller's hand";
    }

    @Override
    public String toString() {
        return "1";
    }
}
