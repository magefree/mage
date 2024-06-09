package mage.cards.b;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.abilities.effects.mana.AddManaAnyColorAttachedControllerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.EnchantedTappedTriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlightedBurgeoning extends CardImpl {

    public BlightedBurgeoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Blighted Burgeoning enters the battlefield, incubate 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new IncubateEffect(2)));

        // Whenever enchanted land is tapped for mana, its controller adds an additional one mana of any color.
        this.addAbility(new EnchantedTappedTriggeredManaAbility(new AddManaAnyColorAttachedControllerEffect()));
    }

    private BlightedBurgeoning(final BlightedBurgeoning card) {
        super(card);
    }

    @Override
    public BlightedBurgeoning copy() {
        return new BlightedBurgeoning(this);
    }
}
