
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToBattlefieldAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class GiftOfImmortality extends CardImpl {

    public GiftOfImmortality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // When enchanted creature dies, return that card to the battlefield under its owner's control.
        // Return Gift of Immortality to the battlefield attached to that creature at the beginning of the next end step.
        this.addAbility(new DiesAttachedTriggeredAbility(new GiftOfImmortalityEffect(), "enchanted creature", false));
    }

    private GiftOfImmortality(final GiftOfImmortality card) {
        super(card);
    }

    @Override
    public GiftOfImmortality copy() {
        return new GiftOfImmortality(this);
    }
}

class GiftOfImmortalityEffect extends OneShotEffect {

    public GiftOfImmortalityEffect() {
        super(Outcome.Benefit);
        this.staticText = "return that card to the battlefield under its owner's control. Return {this} to the battlefield attached to that creature at the beginning of the next end step";
    }

    public GiftOfImmortalityEffect(final GiftOfImmortalityEffect effect) {
        super(effect);
    }

    @Override
    public GiftOfImmortalityEffect copy() {
        return new GiftOfImmortalityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || enchantment == null || enchantment.getAttachedTo() == null) {
            return false;
        }
        Permanent enchanted = (Permanent) game.getLastKnownInformation(enchantment.getAttachedTo(), Zone.BATTLEFIELD);
        Card card = game.getCard(enchantment.getAttachedTo());
        if (card == null || enchanted == null || card.getZoneChangeCounter(game) != enchanted.getZoneChangeCounter(game) + 1) {
            return false;
        }

        controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, true, null);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return false;
        }

        // Create delayed triggered ability
        Effect effect = new ReturnToBattlefieldAttachedEffect();
        effect.setTargetPointer(new FixedTarget(permanent, game));
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
        return true;
    }
}
