package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AspectOfLamprey extends CardImpl {

    public AspectOfLamprey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Aspect of Lamprey enters the battlefield, target opponent discards two cards.
        ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(2));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Enchanted creature has lifelink.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                LifelinkAbility.getInstance(), AttachmentType.AURA
        )));
    }

    private AspectOfLamprey(final AspectOfLamprey card) {
        super(card);
    }

    @Override
    public AspectOfLamprey copy() {
        return new AspectOfLamprey(this);
    }
}
