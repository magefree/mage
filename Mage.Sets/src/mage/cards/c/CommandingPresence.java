package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.HumanSoldierToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CommandingPresence extends CardImpl {

    public CommandingPresence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +2/+2 and has first strike and "Whenever this creature deals combat damage to a player, create a 1/1 white Human Soldier creature token."
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                FirstStrikeAbility.getInstance(), AttachmentType.AURA,
                Duration.WhileOnBattlefield, "and has first strike"
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                new DealsCombatDamageToAPlayerTriggeredAbility(
                        new CreateTokenEffect(new HumanSoldierToken()), false
                ), AttachmentType.AURA, Duration.WhileOnBattlefield,
                "and \"Whenever this creature deals combat damage to a player, " +
                        "create a 1/1 white Human Soldier creature token.\""
        ));
        this.addAbility(ability);
    }

    private CommandingPresence(final CommandingPresence card) {
        super(card);
    }

    @Override
    public CommandingPresence copy() {
        return new CommandingPresence(this);
    }
}
