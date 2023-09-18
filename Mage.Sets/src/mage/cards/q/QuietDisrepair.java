
package mage.cards.q;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyAttachedToEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class QuietDisrepair extends CardImpl {

    public QuietDisrepair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}");
        this.subtype.add(SubType.AURA);


        // Enchant artifact or enchantment
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // At the beginning of your upkeep, choose one - Destroy enchanted permanent; or you gain 2 life.
        ability = new BeginningOfUpkeepTriggeredAbility(new DestroyAttachedToEffect("enchanted permanent"), TargetController.YOU, false);
        Mode mode = new Mode(new GainLifeEffect(2));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private QuietDisrepair(final QuietDisrepair card) {
        super(card);
    }

    @Override
    public QuietDisrepair copy() {
        return new QuietDisrepair(this);
    }
}
