
package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.GainLifeEffect;
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
 * @author LevelX2
 */
public final class VerdantHaven extends CardImpl {

    public VerdantHaven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Verdant Haven enters the battlefield, you gain 2 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(2)));

        // Whenever enchanted land is tapped for mana, its controller adds one mana of any color.
        this.addAbility(new EnchantedTappedTriggeredManaAbility(new AddManaAnyColorAttachedControllerEffect()));
    }

    private VerdantHaven(final VerdantHaven card) {
        super(card);
    }

    @Override
    public VerdantHaven copy() {
        return new VerdantHaven(this);
    }
}
