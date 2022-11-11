
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.LoseAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class Earthbind extends CardImpl {

    public Earthbind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.LoseAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Earthbind enters the battlefield, if enchanted creature has flying, Earthbind deals 2 damage to that creature and Earthbind gains "Enchanted creature loses flying."
        Ability ability2 = new EntersBattlefieldTriggeredAbility(new EarthbindEffect());
        Effect effect = new LoseAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA);
        effect.setText("");
        ability2.addEffect(effect);        
        this.addAbility(ability2);
    }

    private Earthbind(final Earthbind card) {
        super(card);
    }

    @Override
    public Earthbind copy() {
        return new Earthbind(this);
    }
}

class EarthbindEffect extends OneShotEffect {

    public EarthbindEffect() {
        super(Outcome.Damage);
        staticText = "if enchanted creature has flying, {this} deals 2 damage to that creature and Earthbind gains \"Enchanted creature loses flying.\"";
    }

    public EarthbindEffect(final EarthbindEffect effect) {
        super(effect);
    }

    @Override
    public EarthbindEffect copy() {
        return new EarthbindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        if (attachment != null && attachment.getAttachedTo() != null) {           
            Permanent enchanted = game.getPermanent(attachment.getAttachedTo());
            if (enchanted != null) {
                if (enchanted.getAbilities().contains(FlyingAbility.getInstance())) {               
                    enchanted.damage(2, source.getSourceId(), source, game, false, true);
                }
                return true;
            }
        }

        return false;
    }
}


