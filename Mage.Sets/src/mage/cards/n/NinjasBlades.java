package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.JobSelectAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NinjasBlades extends CardImpl {

    public NinjasBlades(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // Job select
        this.addAbility(new JobSelectAbility());

        // Equipped creature gets +1/+1, is a Ninja in addition to its other types, and has "Whenever this creature deals combat damage to a player, draw a card, then discard a card. That player loses life equal to the discarded card's mana value."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new AddCardSubtypeAttachedEffect(
                SubType.NINJA, AttachmentType.EQUIPMENT
        ).setText(", is a Ninja in addition to its other types"));
        ability.addEffect(new GainAbilityAttachedEffect(
                new DealsCombatDamageToAPlayerTriggeredAbility(
                        new NinjasBladesEffect(), false, true
                ), AttachmentType.EQUIPMENT
        ).setText(", and has \"Whenever this creature deals combat damage to a player, draw a card, " +
                "then discard a card. That player loses life equal to the discarded card's mana value.\""));
        this.addAbility(ability);

        // Mutsunokami -- Equip {2}
        this.addAbility(new EquipAbility(2).withFlavorWord("Mutsunokami"));
    }

    private NinjasBlades(final NinjasBlades card) {
        super(card);
    }

    @Override
    public NinjasBlades copy() {
        return new NinjasBlades(this);
    }
}

class NinjasBladesEffect extends OneShotEffect {

    NinjasBladesEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card, then discard a card. " +
                "That player loses life equal to the discarded card's mana value";
    }

    private NinjasBladesEffect(final NinjasBladesEffect effect) {
        super(effect);
    }

    @Override
    public NinjasBladesEffect copy() {
        return new NinjasBladesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.drawCards(1, source, game);
        Card card = controller.discardOne(false, false, source, game);
        if (card == null || card.getManaValue() < 1) {
            return true;
        }
        Optional.ofNullable(getTargetPointer().getFirst(game, source))
                .map(game::getPlayer)
                .ifPresent(player -> player.loseLife(card.getManaValue(), game, source, false));
        return true;
    }
}
