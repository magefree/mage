package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class UnholyIndenture extends CardImpl {

    public UnholyIndenture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // When enchanted creature dies, return that card to the battlefield under your control with a +1/+1 counter on it.
        this.addAbility(new DiesAttachedTriggeredAbility(new UnholyIndentureReturnEffect(), "enchanted creature"));
    }

    public UnholyIndenture(final UnholyIndenture card) {
        super(card);
    }

    @Override
    public UnholyIndenture copy() {
        return new UnholyIndenture(this);
    }
}

class UnholyIndentureReturnEffect extends OneShotEffect {

    public UnholyIndentureReturnEffect() {
        super(Outcome.Benefit);
        staticText = "return that card to the battlefield under your control with a +1/+1 counter on it";
    }

    public UnholyIndentureReturnEffect(final UnholyIndentureReturnEffect effect) {
        super(effect);
    }

    @Override
    public UnholyIndentureReturnEffect copy() {
        return new UnholyIndentureReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // copy from ReturnToBattlefieldUnderYourControlAttachedEffect
        Object object = getValue("attachedTo");
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && object instanceof Permanent
                && !(object instanceof PermanentToken)) { // not token
            Card card = game.getCard(((Permanent) object).getId());
            // Move the card only, if it is still in the next zone after the battlefield
            if (card != null && card.getZoneChangeCounter(game) == ((Permanent) object).getZoneChangeCounter(game) + 1) {
                Counters countersToAdd = new Counters();
                countersToAdd.addCounter(CounterType.P1P1.createInstance());
                game.setEnterWithCounters(card.getId(), countersToAdd);
                controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, false, null);
                return true;
            }
        }

        return false;
    }

}