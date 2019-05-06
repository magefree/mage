package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.SpellAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;
import mage.abilities.mana.ManaAbility;
import mage.game.stack.Spell;

/**
 * @author TheElk801
 */
public final class DovinHandOfControl extends CardImpl {

    public DovinHandOfControl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{W/U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DOVIN);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(5));

        // Artifact, instant, and sorcery spells your opponents cast cost {1} more to cast.
        this.addAbility(new SimpleStaticAbility(new DovinHandOfControlEffect()));

        // -1: Until your next turn, prevent all damage that would be dealt to and dealt by target permanent an opponent controls.
        Ability ability = new LoyaltyAbility(new PreventDamageToTargetEffect(
                Duration.UntilYourNextTurn
        ).setText("Until your next turn, prevent all damage that would be dealt to"), -1);
        ability.addEffect(new PreventDamageByTargetEffect(
                Duration.UntilYourNextTurn
        ).setText("and dealt by target permanent an opponent controls"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT));
        this.addAbility(ability);
    }

    private DovinHandOfControl(final DovinHandOfControl card) {
        super(card);
    }

    @Override
    public DovinHandOfControl copy() {
        return new DovinHandOfControl(this);
    }
}

class DovinHandOfControlEffect extends CostModificationEffectImpl {

    DovinHandOfControlEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Artifact, instant, and sorcery spells your opponents cast cost {1} more to cast";
    }

    private DovinHandOfControlEffect(DovinHandOfControlEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        CardUtil.adjustCost(spellAbility, -1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        Card card = game.getCard(abilityToModify.getSourceId());
        if (!(abilityToModify instanceof SpellAbility)) {
            return false;
        }
        return card != null
                && (card.isInstantOrSorcery()
                || card.isArtifact())
                && game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId());
    }

    @Override
    public DovinHandOfControlEffect copy() {
        return new DovinHandOfControlEffect(this);
    }
}
