package mage.cards.e;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.mana.DynamicManaEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.EnchantedTappedTriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author Eirkei
 */
public final class ElvishGuidance extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ELF, "");
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);

    public ElvishGuidance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutManaInPool));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever enchanted land is tapped for mana, its controller adds {G} for each Elf on the battlefield.
        this.addAbility(new EnchantedTappedTriggeredManaAbility(new DynamicManaEffect(
                Mana.GreenMana(1), xValue
        ).setText("its controller adds {G} for each Elf on the battlefield")));
    }

    private ElvishGuidance(final ElvishGuidance card) {
        super(card);
    }

    @Override
    public ElvishGuidance copy() {
        return new ElvishGuidance(this);
    }
}
