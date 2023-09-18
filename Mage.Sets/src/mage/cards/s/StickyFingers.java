package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.AttachmentType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class StickyFingers extends CardImpl {

    public StickyFingers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature has menace and "Whenever this creature deals combat damage to a player, create a Treasure token.
        Ability ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(new MenaceAbility(false), AttachmentType.AURA));
        ability.addEffect(new GainAbilityAttachedEffect(new DealsCombatDamageToAPlayerTriggeredAbility(new CreateTokenEffect(new TreasureToken()), false), AttachmentType.AURA)
                .setText("and \"Whenever this creature deals combat damage to a player, create a Treasure token.\" <i>(It can't be blocked except by two or more creatures. The token is an artifact with \"{T}, Sacrifice this artifact: Add one mana of any color.\")</i>"));
        this.addAbility(ability);

        // When enchanted creature dies, draw a card.
        this.addAbility(new DiesAttachedTriggeredAbility(new DrawCardSourceControllerEffect(1), "enchanted creature"));
    }

    private StickyFingers(final StickyFingers card) {
        super(card);
    }

    @Override
    public StickyFingers copy() {
        return new StickyFingers(this);
    }
}
