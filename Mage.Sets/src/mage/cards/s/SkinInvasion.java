
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SkinInvasion extends CardImpl {

    public SkinInvasion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{R}");
        this.subtype.add(SubType.AURA);

        this.secondSideCardClazz = SkinShedder.class;

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature attacks each combat if able.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new AttacksIfAbleAttachedEffect(Duration.WhileOnBattlefield, AttachmentType.AURA)));

        // When enchanted creature dies, return Skin Invasion to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        this.addAbility(new DiesAttachedTriggeredAbility(new SkinInvasionEffect(), "enchanted creature", false));
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

    public SkinInvasionEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return {this} to the battlefield transformed under your control";
    }

    public SkinInvasionEffect(final SkinInvasionEffect effect) {
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
            game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            return true;
        }
        return false;
    }

}
