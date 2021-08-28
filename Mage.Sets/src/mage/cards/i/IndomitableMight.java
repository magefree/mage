package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IndomitableMight extends CardImpl {

    public IndomitableMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +3/+3.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(3, 3)));

        // Enchanted creature's controller may have it assign combat damage as though it weren't blocked.
        this.addAbility(new SimpleStaticAbility(new IndomitableMightEffect()));
    }

    private IndomitableMight(final IndomitableMight card) {
        super(card);
    }

    @Override
    public IndomitableMight copy() {
        return new IndomitableMight(this);
    }
}

class IndomitableMightEffect extends AsThoughEffectImpl {

    IndomitableMightEffect() {
        super(AsThoughEffectType.DAMAGE_NOT_BLOCKED, Duration.WhileOnBattlefield, Outcome.Damage);
        this.staticText = "enchanted creature's controller may have it " +
                "assign its combat damage as though it weren't blocked";
    }

    private IndomitableMightEffect(IndomitableMightEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        if (sourcePermanent == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(sourcePermanent.getAttachedTo());
        if (permanent == null) {
            return false;
        }
        Player controller = game.getPlayer(permanent.getControllerId());
        return controller != null
                && controller.chooseUse(Outcome.Damage, "Have " + permanent.getLogName()
                + " assign damage as though it weren't blocked?", source, game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public IndomitableMightEffect copy() {
        return new IndomitableMightEffect(this);
    }
}
