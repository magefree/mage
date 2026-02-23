package mage.cards.m;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.functions.CopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MirrorhallMimic extends TransformingDoubleFacedCard {

    public MirrorhallMimic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT}, "{3}{U}",
                "Ghastly Mimicry",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA}, "U"
        );

        // Mirrorhall Mimic
        this.getLeftHalfCard().setPT(0, 0);

        // You may have Mirrorhall Mimic enter the battlefield as a copy of any creature on the battlefield, except it's a Spirit in addition to its other types.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldAbility(new CopyPermanentEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE, new MirrorhallMimicApplier()
        ), true, null, "You may have {this} enter as a copy of any creature on the battlefield, except it's a Spirit in addition to its other types.", null));


        // Ghastly Mimicry
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getRightHalfCard().getSpellAbility().addTarget(auraTarget);
        this.getRightHalfCard().getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.getRightHalfCard().addAbility(new EnchantAbility(auraTarget));

        // Disturb {3}{U}{U}
        // needs to be added after enchant ability is set for target
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{3}{U}{U}"));

        // At the beginning of your upkeep, create a token that's a copy of enchanted creature, except it's a Spirit in addition to its other types.
        this.getRightHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(new GhastlyMimicryEffect()));

        // If Ghastly Mimicry would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeBackAbility());
    }

    private MirrorhallMimic(final MirrorhallMimic card) {
        super(card);
    }

    @Override
    public MirrorhallMimic copy() {
        return new MirrorhallMimic(this);
    }
}

class MirrorhallMimicApplier extends CopyApplier {

    @Override
    public boolean apply(Game game, MageObject blueprint, Ability source, UUID targetObjectId) {
        blueprint.addSubType(SubType.SPIRIT);
        return true;
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
