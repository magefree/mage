package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayTargetPlayerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author LevelX2
 */
public final class PatternOfRebirth extends CardImpl {

    public PatternOfRebirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When enchanted creature dies, that creature's controller may search their library for a creature card and put that card onto the battlefield. If that player does, they shuffle their library.
        Effect effect = new SearchLibraryPutInPlayTargetPlayerEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_CREATURE), false);
        effect.setText("that creature's controller may search their library for a creature card and put that card onto the battlefield. If that player does, they shuffle");
        this.addAbility(new DiesAttachedTriggeredAbility(effect, "enchanted creature", true, true, SetTargetPointer.ATTACHED_TO_CONTROLLER));

    }

    private PatternOfRebirth(final PatternOfRebirth card) {
        super(card);
    }

    @Override
    public PatternOfRebirth copy() {
        return new PatternOfRebirth(this);
    }
}
