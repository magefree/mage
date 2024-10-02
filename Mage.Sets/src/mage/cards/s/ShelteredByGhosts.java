package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShelteredByGhosts extends CardImpl {

    public ShelteredByGhosts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Sheltered by Ghosts enters, exile target nonland permanent an opponent controls until Sheltered by Ghosts leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_NON_LAND));
        this.addAbility(ability);

        // Enchanted creature gets +1/+0 and has lifelink and ward {2}.
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                LifelinkAbility.getInstance(), AttachmentType.AURA
        ).setText("and has lifelink"));
        ability.addEffect(new GainAbilityAttachedEffect(
                new WardAbility(new GenericManaCost(2)), AttachmentType.AURA
        ).setText("and ward {2}"));
        this.addAbility(ability);
    }

    private ShelteredByGhosts(final ShelteredByGhosts card) {
        super(card);
    }

    @Override
    public ShelteredByGhosts copy() {
        return new ShelteredByGhosts(this);
    }
}
