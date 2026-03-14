package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class SkinInvasion extends TransformingDoubleFacedCard {

    public SkinInvasion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA}, "{R}",
                "Skin Shedder",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.INSECT, SubType.HORROR}, "R"
        );

        // Skin Invasion
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getLeftHalfCard().getSpellAbility().addTarget(auraTarget);
        this.getLeftHalfCard().getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.getLeftHalfCard().addAbility(ability);

        // Enchanted creature attacks each combat if able.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(
                new AttacksIfAbleAttachedEffect(Duration.WhileOnBattlefield, AttachmentType.AURA)));

        // When enchanted creature dies, return Skin Invasion to the battlefield transformed under your control.
        this.getLeftHalfCard().addAbility(new DiesAttachedTriggeredAbility(new SkinInvasionEffect(), "enchanted creature", false));

        // Skin Shedder
        this.getRightHalfCard().setPT(3, 4);
    }

    private SkinInvasion(final SkinInvasion card) {
        super(card);
    }

    @Override
    public SkinInvasion copy() {
        return new SkinInvasion(this);
    }
}

class SkinInvasionEffect extends OneShotEffect {

    SkinInvasionEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return this card to the battlefield transformed under your control";
    }

    private SkinInvasionEffect(final SkinInvasionEffect effect) {
        super(effect);
    }

    @Override
    public SkinInvasionEffect copy() {
        return new SkinInvasionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (card != null && controller != null) {
            game.getState().setValue(TransformingDoubleFacedCard.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            return true;
        }
        return false;
    }

}
