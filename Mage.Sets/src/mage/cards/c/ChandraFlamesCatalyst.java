package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.CastCardFromGraveyardThenExileItEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.CastFromHandWithoutPayingManaCostEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class ChandraFlamesCatalyst extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
        filter.add(Predicates.or(CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate()));
    }

    public ChandraFlamesCatalyst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{R}{R}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);
        this.setStartingLoyalty(5);

        // +1: Chandra, Flame's Catalyst deals 3 damage to each opponent.
        this.addAbility(new LoyaltyAbility(new DamagePlayersEffect(3, TargetController.OPPONENT), 1));

        // −2: You may cast target red instant or sorcery card from your graveyard. If that spell would be put into your graveyard this turn, exile it instead.
        CastCardFromGraveyardThenExileItEffect minusEffect = new CastCardFromGraveyardThenExileItEffect();
        minusEffect.setText("You may cast target red instant or sorcery card from your graveyard. If that spell would be put into your graveyard this turn, exile it instead");
        Ability ability = new LoyaltyAbility(minusEffect, -2);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // −8: Discard your hand, then draw seven cards. Until end of turn, you may cast spells from your hand without paying their mana costs.
        Effect discardHandEffect = new DiscardHandControllerEffect();
        Effect drawEffect = new DrawCardSourceControllerEffect(7);
        drawEffect.setText(", then draw seven cards");
        Effect castSpellsFromHandEffect = new CastFromHandWithoutPayingManaCostEffect(
                StaticFilters.FILTER_CARD_NON_LAND, true, Duration.EndOfTurn);
        castSpellsFromHandEffect.setText("Until end of turn, you may cast spells from your hand without paying their mana costs");
        Ability ultimateAbility = new LoyaltyAbility(discardHandEffect, -8);
        ultimateAbility.addEffect(drawEffect);
        ultimateAbility.addEffect(castSpellsFromHandEffect);
        this.addAbility(ultimateAbility);
    }

    private ChandraFlamesCatalyst(final ChandraFlamesCatalyst card) {
        super(card);
    }

    @Override
    public ChandraFlamesCatalyst copy() {
        return new ChandraFlamesCatalyst(this);
    }
}
