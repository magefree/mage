package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GhastlyMimicry extends CardImpl {

    public GhastlyMimicry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "");

        this.subtype.add(SubType.AURA);
        this.color.setBlue(true);
        this.nightCard = true;

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // At the beginning of your upkeep, create a token that's a copy of enchanted creature, except it's a Spirit in addition to its other types.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GhastlyMimicryEffect()));

        // If Ghastly Mimicry would be put into a graveyard from anywhere, exile it instead.
        this.addAbility(DisturbAbility.makeBackAbility());
    }

    private GhastlyMimicry(final GhastlyMimicry card) {
        super(card);
    }

    @Override
    public GhastlyMimicry copy() {
        return new GhastlyMimicry(this);
    }
}

class GhastlyMimicryEffect extends OneShotEffect {

    GhastlyMimicryEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of enchanted creature, " +
                "except it's a Spirit in addition to its other types";
    }

    private GhastlyMimicryEffect(final GhastlyMimicryEffect effect) {
        super(effect);
    }

    @Override
    public GhastlyMimicryEffect copy() {
        return new GhastlyMimicryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent == null) {
            return false;
        }
        Permanent attached = game.getPermanent(permanent.getAttachedTo());
        if (attached == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
        effect.withAdditionalSubType(SubType.SPIRIT);
        return effect.setTargetPointer(new FixedTarget(attached, game)).apply(game, source);
    }
}
