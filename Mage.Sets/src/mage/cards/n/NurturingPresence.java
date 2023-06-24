package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NurturingPresence extends CardImpl {

    public NurturingPresence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has "Whenever a creature enters the battlefield under your control, this creature gets +1/+1 until end of turn."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new EntersBattlefieldControlledTriggeredAbility(
                        new BoostSourceEffect(1, 1, Duration.EndOfTurn)
                                .setText("this creature gets +1/+1 until end of turn"),
                        StaticFilters.FILTER_PERMANENT_A_CREATURE
                ), AttachmentType.AURA
        )));

        // When Nurturing Presence enters the battlefield, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SpiritWhiteToken())));
    }

    private NurturingPresence(final NurturingPresence card) {
        super(card);
    }

    @Override
    public NurturingPresence copy() {
        return new NurturingPresence(this);
    }
}
