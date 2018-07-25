
package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.combat.CantAttackAnyPlayerAllEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterEnchantmentCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.command.emblems.FarajoPeacemakerEmblem;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author EikePeace
 */
public final class FarajoPeacemaker extends CardImpl {

    private static final FilterEnchantmentCard filter = new FilterEnchantmentCard("Enchantment card with converted mana cost X");

    UUID ability2Id;
    public FarajoPeacemaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.FARAJO);

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +1: Until your next turn, up to one target creature can't attack and its activated abilities can't be activated.
        Effect effect = new CantAttackAnyPlayerAllEffect(Duration.UntilYourNextTurn, new FilterCreaturePermanent());
        effect.setText("Until your next turn, up to one target creature can't attack");
        Ability ability = new LoyaltyAbility(effect, 1);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        ability.addEffect(new FarajoPeacemakerCantActivateAbilitiesEffect());
        this.addAbility(ability);

        // -X: You gain 2 life and draw a card.
        Ability ability2 = new LoyaltyAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(new FilterEnchantmentCard())));
        ability2Id = ability2.getOriginalId();
        this.addAbility(ability2);


        // -7: You get an emblem with "Your opponents can't untap more than two permanents during their untap steps."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new FarajoPeacemakerEmblem()), -7));
    }

    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(ability2Id)) {
            int cmc = 0;
            for (Cost cost : ability.getCosts()) {
                if (cost instanceof PayVariableLoyaltyCost) {
                    cmc = ((PayVariableLoyaltyCost) cost).getAmount();
                }
            }
            FilterCard newFilter = filter.copy();
            newFilter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, cmc));
            ability.getEffects().clear();
            ability.getTargets().clear();
            ability.addEffect(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)));
        }
    }
    public FarajoPeacemaker(final FarajoPeacemaker card) {
        super(card);
        this.ability2Id = card.ability2Id;
    }

    @Override
    public FarajoPeacemaker copy() {
        return new FarajoPeacemaker(this);
    }
}

class FarajoPeacemakerCantActivateAbilitiesEffect extends ContinuousRuleModifyingEffectImpl {

    FarajoPeacemakerCantActivateAbilitiesEffect() {
        super(Duration.UntilYourNextTurn, Outcome.UnboostCreature);
        staticText = "and its activated abilities can't be activated";
    }

    FarajoPeacemakerCantActivateAbilitiesEffect(final FarajoPeacemakerCantActivateAbilitiesEffect effect) {
        super(effect);
    }

    @Override
    public FarajoPeacemakerCantActivateAbilitiesEffect copy() {
        return new FarajoPeacemakerCantActivateAbilitiesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ACTIVATE_ABILITY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getSourceId().equals(this.getTargetPointer().getFirst(game, source));
    }
}
