package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.DoubleFacedCardHalf;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RadiantGrace extends TransformingDoubleFacedCard {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures enchanted player controls");

    static {
        filter.add(TargetController.ENCHANTED.getControllerPredicate());
    }

    public RadiantGrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA}, "{W}",
                "Radiant Restraints",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA, SubType.CURSE}, "W"
        );

        // Radiant Grace
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getLeftHalfCard().getSpellAbility().addTarget(auraTarget);
        this.getLeftHalfCard().getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.getLeftHalfCard().addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +1/+0 and has vigilance.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                VigilanceAbility.getInstance(), AttachmentType.AURA
        ).setText("and has vigilance"));
        this.getLeftHalfCard().addAbility(ability);

        // When enchanted creature dies, return Radiant Grace to the battlefield transformed under your control attached to target opponent.
        ability = new DiesAttachedTriggeredAbility(
                new RadiantGraceEffect(), "enchanted creature", false
        );
        ability.addTarget(new TargetOpponent());
        this.getLeftHalfCard().addAbility(ability);

        // Radiant Restraints
        // Enchant player
        TargetPlayer auraTarget2 = new TargetPlayer();
        this.getRightHalfCard().getSpellAbility().addTarget(auraTarget2);
        this.getRightHalfCard().getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.getRightHalfCard().addAbility(new EnchantAbility(auraTarget2));

        // Creatures enchanted player controls enter the battlefield tapped.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new PermanentsEnterBattlefieldTappedEffect(filter)));
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
        staticText = "return this card to the battlefield transformed under your control attached to target opponent";
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

        DoubleFacedCardHalf card = (DoubleFacedCardHalf) game.getCard(source.getSourceId());
        if (card == null) {
            return false;
        }

        game.getState().setValue(TransformingDoubleFacedCard.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
        game.getState().setValue("attachTo:" + card.getOtherSide().getId(), player.getId());
        if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
            player.addAttachment(card.getId(), source, game);
        }
        return true;
    }
}
