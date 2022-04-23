package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RadiantGrace extends CardImpl {

    public RadiantGrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.subtype.add(SubType.AURA);
        this.secondSideCardClazz = mage.cards.r.RadiantRestraints.class;

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Enchanted creature gets +1/+0 and has vigilance.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                VigilanceAbility.getInstance(), AttachmentType.AURA
        ).setText("and has vigilance"));
        this.addAbility(ability);

        // When enchanted creature dies, return Radiant Grace to the battlefield transformed under your control attached to target opponent.
        this.addAbility(new TransformAbility());
        ability = new DiesAttachedTriggeredAbility(
                new RadiantGraceEffect(), "enchanted creature", false
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private RadiantGrace(final RadiantGrace card) {
        super(card);
    }

    @Override
    public RadiantGrace copy() {
        return new RadiantGrace(this);
    }
}

class RadiantGraceEffect extends OneShotEffect {

    RadiantGraceEffect() {
        super(Outcome.Benefit);
        staticText = "return {this} to the battlefield transformed under your control attached to target opponent";
    }

    private RadiantGraceEffect(final RadiantGraceEffect effect) {
        super(effect);
    }

    @Override
    public RadiantGraceEffect copy() {
        return new RadiantGraceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller == null || player == null
                || game.getState().getZone(source.getSourceId()) != Zone.GRAVEYARD) {
            return false;
        }

        Card card = game.getCard(source.getSourceId());
        if (card == null) {
            return false;
        }

        game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
        game.getState().setValue("attachTo:" + source.getSourceId(), player.getId());
        if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
            player.addAttachment(card.getId(), source, game);
        }
        return true;
    }
}
