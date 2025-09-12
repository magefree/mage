package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAnyReturnSourceFromGraveyardAttachedToItTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SmokeShroud extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.NINJA, "a Ninja you control");

    public SmokeShroud(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 and has flying.
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.AURA
        ).setText("and has flying"));
        this.addAbility(ability);

        // When a Ninja you control enters, you may return Smoke Shroud from your graveyard to the battlefield attached to that creature.
        this.addAbility(new EntersBattlefieldAnyReturnSourceFromGraveyardAttachedToItTriggeredAbility(filter));
    }

    private SmokeShroud(final SmokeShroud card) {
        super(card);
    }

    @Override
    public SmokeShroud copy() {
        return new SmokeShroud(this);
    }
}
