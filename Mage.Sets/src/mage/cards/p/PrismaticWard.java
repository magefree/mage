
package mage.cards.p;

import java.util.UUID;
import mage.MageObject;
import mage.ObjectColor;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class PrismaticWard extends CardImpl {

    public PrismaticWard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // As Prismatic Ward enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Benefit)));

        // Prevent all damage that would be dealt to enchanted creature by sources of the chosen color.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PrismaticWardPreventDamageEffect()));
    }

    private PrismaticWard(final PrismaticWard card) {
        super(card);
    }

    @Override
    public PrismaticWard copy() {
        return new PrismaticWard(this);
    }
}

class PrismaticWardPreventDamageEffect extends PreventionEffectImpl {

    public PrismaticWardPreventDamageEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false);
        staticText = "Prevent all damage that would be dealt to enchanted creature by sources of the chosen color.";
    }

    public PrismaticWardPreventDamageEffect(final PrismaticWardPreventDamageEffect effect) {
        super(effect);
    }

    @Override
    public PrismaticWardPreventDamageEffect copy() {
        return new PrismaticWardPreventDamageEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!super.applies(event, source, game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        ObjectColor color = (ObjectColor) game.getState().getValue(permanent.getId() + "_color");
        if (color == null) {
            return false;
        }
        MageObject sourceObject = game.getObject(event.getSourceId());
        if (sourceObject == null || !sourceObject.getColor(game).shares(color)) {
            return false;
        }
        Permanent attachment = game.getPermanent(source.getSourceId());
        if (attachment != null
                && attachment.getAttachedTo() != null
                && event.getTargetId().equals(attachment.getAttachedTo())) {
            return true;
        }
        return false;
    }
}
