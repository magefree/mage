package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOpponentTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.ControlEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class IllusoryGains extends CardImpl {

    public IllusoryGains(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.GainControl));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // You control enchanted creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ControlEnchantedEffect()));

        // Whenever a creature enters the battlefield under an opponent's control, attach Illusory Gains to that creature.
        this.addAbility(new EntersBattlefieldOpponentTriggeredAbility(
                Zone.BATTLEFIELD, new IllusoryGainsEffect(), StaticFilters.FILTER_PERMANENT_CREATURE, false, SetTargetPointer.PERMANENT));
    }

    private IllusoryGains(final IllusoryGains card) {
        super(card);
    }

    @Override
    public IllusoryGains copy() {
        return new IllusoryGains(this);
    }
}

class IllusoryGainsEffect extends OneShotEffect {

    IllusoryGainsEffect() {
        super(Outcome.Detriment);
        staticText = "attach {this} to that creature";
    }

    private IllusoryGainsEffect(final IllusoryGainsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Permanent opponentCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        Permanent illusoryGains = game.getPermanent(source.getSourceId());
        if (you != null && opponentCreature != null && illusoryGains != null) {
            Permanent oldCreature = game.getPermanent(illusoryGains.getAttachedTo());
            if (oldCreature == null) {
                return false;
            }
            if (oldCreature.removeAttachment(illusoryGains.getId(), source, game)) {
                return opponentCreature.addAttachment(illusoryGains.getId(), source, game);
            }
        }
        return false;
    }

    @Override
    public IllusoryGainsEffect copy() {
        return new IllusoryGainsEffect(this);
    }
}
