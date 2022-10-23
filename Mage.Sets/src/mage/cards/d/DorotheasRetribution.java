package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
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
public final class DorotheasRetribution extends CardImpl {

    public DorotheasRetribution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "");

        this.subtype.add(SubType.AURA);
        this.color.setWhite(true);
        this.color.setBlue(true);
        this.nightCard = true;

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has "Whenever this creature attacks, create a 4/4 white Spirit creature token with flying that's tapped and attacking. Sacrifice that token at end of combat."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new AttacksTriggeredAbility(new DorotheasRetributionEffect())
                        .setTriggerPhrase("Whenever this creature attacks, "),
                AttachmentType.AURA
        )));

        // If Dorothea's Retribution would be put into a graveyard from anywhere, exile it instead.
        this.addAbility(new PutIntoGraveFromAnywhereSourceAbility(new ExileSourceEffect().setText("exile it instead")));
    }

    private DorotheasRetribution(final DorotheasRetribution card) {
        super(card);
    }

    @Override
    public DorotheasRetribution copy() {
        return new DorotheasRetribution(this);
    }
}

class DorotheasRetributionEffect extends OneShotEffect {

    DorotheasRetributionEffect() {
        super(Outcome.Benefit);
        staticText = "create a 4/4 white Spirit creature token with flying " +
                "that's tapped and attacking. Sacrifice that token at end of combat";
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
                        .setText("sacrifce that token")
        ), source);
        return true;
    }
}
