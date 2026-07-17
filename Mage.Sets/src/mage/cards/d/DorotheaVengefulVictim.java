package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.token.DorotheasRetributionSpiritToken;
import mage.game.permanent.token.Token;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DorotheaVengefulVictim extends TransformingDoubleFacedCard {

    public DorotheaVengefulVictim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT}, "{W}{U}",
                "Dorothea's Retribution",
                new SuperType[]{}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA}, "WU");

        // Dorothea, Vengeful Victim
        this.getLeftHalfCard().setPT(4, 4);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // When Dorothea, Vengeful Victim attacks or blocks, sacrifice it at end of combat.
        this.getLeftHalfCard().addAbility(new AttacksOrBlocksTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheEndOfCombatDelayedTriggeredAbility(new SacrificeSourceEffect())
        ).setText("sacrifice it at end of combat"), false));


        // Dorothea's Retribution
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getRightHalfCard().getSpellAbility().addTarget(auraTarget);
        this.getRightHalfCard().getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.getRightHalfCard().addAbility(new EnchantAbility(auraTarget));

        // Disturb {1}{W}{U}
        // needs to be added after right half has spell ability target set
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{1}{W}{U}"));

        // Enchanted creature has "Whenever this creature attacks, create a 4/4 white Spirit creature token with flying that's tapped and attacking. Sacrifice that token at end of combat."
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new AttacksTriggeredAbility(new DorotheasRetributionEffect()).setTriggerPhrase("Whenever this creature attacks, "),
                AttachmentType.AURA
        )));

        // If Dorothea's Retribution would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeBackAbility());
    }

    private DorotheaVengefulVictim(final DorotheaVengefulVictim card) {
        super(card);
    }

    @Override
    public DorotheaVengefulVictim copy() {
        return new DorotheaVengefulVictim(this);
    }
}

class DorotheasRetributionEffect extends OneShotEffect {

    DorotheasRetributionEffect() {
        super(Outcome.Benefit);
        staticText = "create a 4/4 white Spirit creature token with flying that's tapped and attacking. Sacrifice that token at end of combat";
    }

    private DorotheasRetributionEffect(final DorotheasRetributionEffect effect) {
        super(effect);
    }

    @Override
    public DorotheasRetributionEffect copy() {
        return new DorotheasRetributionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new DorotheasRetributionSpiritToken();
        token.putOntoBattlefield(1, game, source, source.getControllerId(), true, true);
        game.addDelayedTriggeredAbility(new AtTheEndOfCombatDelayedTriggeredAbility(
                new SacrificeTargetEffect()
                        .setTargetPointer(new FixedTargets(token, game))
                        .setText("sacrifice that token")
        ), source);
        return true;
    }
}
