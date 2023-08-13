package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAttachedEffect;
import mage.abilities.effects.common.combat.CantAttackControllerAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FealtyToTheRealm extends CardImpl {

    public FealtyToTheRealm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Fealty to the Realm enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()));

        // The monarch controls enchanted creature.
        this.addAbility(new SimpleStaticAbility(new FealtyToTheRealmEffect()));

        // Enchanted creature attacks each combat if able and can't attack you.
        Ability ability = new SimpleStaticAbility(
                new AttacksIfAbleAttachedEffect(Duration.WhileOnBattlefield, AttachmentType.AURA)
        );
        ability.addEffect(new CantAttackControllerAttachedEffect(AttachmentType.AURA, false).setText("and can't attack you"));
        this.addAbility(ability);
    }

    private FealtyToTheRealm(final FealtyToTheRealm card) {
        super(card);
    }

    @Override
    public FealtyToTheRealm copy() {
        return new FealtyToTheRealm(this);
    }
}

class FealtyToTheRealmEffect extends ContinuousEffectImpl {

    public FealtyToTheRealmEffect() {
        super(Duration.WhileOnBattlefield, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "the monarch controls enchanted creature";
    }

    public FealtyToTheRealmEffect(final FealtyToTheRealmEffect effect) {
        super(effect);
    }

    @Override
    public FealtyToTheRealmEffect copy() {
        return new FealtyToTheRealmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = source.getSourcePermanentIfItStillExists(game);
        if (enchantment == null || game.getMonarchId() == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
        if (permanent == null) {
            return false;
        }
        permanent.changeControllerId(game.getMonarchId(), game, source);
        return true;
    }
}
