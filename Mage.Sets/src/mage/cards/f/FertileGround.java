package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
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
 * @author Plopman
 */
public final class FertileGround extends CardImpl {

    public FertileGround(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever enchanted land is tapped for mana, its controller adds one mana of any color.
        this.addAbility(new EnchantedTappedTriggeredManaAbility(new AddManaAnyColorAttachedControllerEffect()));
    }

    private FertileGround(final FertileGround card) {
        super(card);
    }

    @Override
    public FertileGround copy() {
        return new FertileGround(this);
    }
}
