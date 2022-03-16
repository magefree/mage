package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class MirrorMockery extends CardImpl {

    public MirrorMockery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Copy));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted creature attacks, you may create a token that's a copy of that creature. Exile that token at the end of combat.
        this.addAbility(new AttacksAttachedTriggeredAbility(new MirrorMockeryEffect(), AttachmentType.AURA, true));
    }

    private MirrorMockery(final MirrorMockery card) {
        super(card);
    }

    @Override
    public MirrorMockery copy() {
        return new MirrorMockery(this);
    }
}

class MirrorMockeryEffect extends OneShotEffect {

    public MirrorMockeryEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may create a token that's a copy of that creature. Exile that token at end of combat";
    }

    public MirrorMockeryEffect(final MirrorMockeryEffect effect) {
        super(effect);
    }

    @Override
    public MirrorMockeryEffect copy() {
        return new MirrorMockeryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
       // In the case that the enchantment is blinked
        Permanent enchantment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if (enchantment == null) {
            // It was not blinked, use the standard method
            enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        }
        if (enchantment == null) {
            return false;
        }
        Permanent enchanted = game.getPermanentOrLKIBattlefield(enchantment.getAttachedTo());
        if (enchanted != null) {
            CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
            effect.setTargetPointer(new FixedTarget(enchanted, game));
            effect.apply(game, source);
            for (Permanent addedToken : effect.getAddedPermanents()) {
                if (addedToken != null) {
                    ExileTargetEffect exileEffect = new ExileTargetEffect();
                    exileEffect.setTargetPointer(new FixedTarget(addedToken, game));
                    game.addDelayedTriggeredAbility(new AtTheEndOfCombatDelayedTriggeredAbility(exileEffect), source);
                }
            }
            return true;
        }
        return false;
    }
}
