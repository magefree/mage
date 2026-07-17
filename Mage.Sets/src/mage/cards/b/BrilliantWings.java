package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrilliantWings extends CardImpl {

    public BrilliantWings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature has flying and hexproof.
        Ability ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.AURA
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                HexproofAbility.getInstance(), AttachmentType.AURA
        ).setText("and hexproof"));
        this.addAbility(ability);

        // Whenever a creature you control enters, you may pay {1}. If you do, attach this Aura to that creature.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD,
                new DoIfCostPaid(
                        new AttachEffect(Outcome.BoostCreature, "attach this Aura to that creature"),
                        new GenericManaCost(1)
                ), StaticFilters.FILTER_CONTROLLED_CREATURE,
                false, SetTargetPointer.PERMANENT
        ));
    }

    private BrilliantWings(final BrilliantWings card) {
        super(card);
    }

    @Override
    public BrilliantWings copy() {
        return new BrilliantWings(this);
    }
}
