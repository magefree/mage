package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FirstTargetPointer;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HarnessTheStorm extends CardImpl {

    private static final FilterCard filter = new FilterCard("card with the same name as that spell from your graveyard");

    public HarnessTheStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // Whenever you cast an instant or sorcery spell from your hand, you may cast 
        // target card with the same name as that spell from your graveyard.
        Ability ability = new SpellCastControllerTriggeredAbility(
                Zone.BATTLEFIELD, new MayCastTargetCardEffect(false), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY,
                false, SetTargetPointer.SPELL, Zone.HAND
        );
        ability.addTarget(new TargetCardInYourGraveyard(filter)); // Only used for text generation
        ability.setTargetAdjuster(HarnessTheStormAdjuster.instance);
        this.addAbility(ability);
    }

    private HarnessTheStorm(final HarnessTheStorm card) {
        super(card);
    }

    @Override
    public HarnessTheStorm copy() {
        return new HarnessTheStorm(this);
    }

}

enum HarnessTheStormAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        UUID spellId = ability.getEffects().get(0).getTargetPointer().getFirst(game, ability);
        ability.getTargets().clear();
        ability.getAllEffects().setTargetPointer(new FirstTargetPointer());

        Spell spell = game.getSpellOrLKIStack(spellId);
        if (spell == null) {
            return;
        }
        FilterCard filter = new FilterCard("a card named " + spell.getName() + " in your graveyard");
        filter.add(new NamePredicate(spell.getName()));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
    }
}
