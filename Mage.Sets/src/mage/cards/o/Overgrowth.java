package mage.cards.o;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.mana.AddManaToManaPoolTargetControllerEffect;
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
public final class Overgrowth extends CardImpl {

    public Overgrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever enchanted land is tapped for mana, its controller adds {G}{G}.
        this.addAbility(new EnchantedTappedTriggeredManaAbility(new AddManaToManaPoolTargetControllerEffect(
                Mana.GreenMana(2), "their"
        )));
    }

    private Overgrowth(final Overgrowth card) {
        super(card);
    }

    @Override
    public Overgrowth copy() {
        return new Overgrowth(this);
    }
}
