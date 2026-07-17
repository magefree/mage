package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.TargetManaValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.*;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.PrepareCard;
import mage.constants.SubType;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author Grath
 */
public final class GraveResearcher extends PrepareCard {

    private static final Condition condition = new CardsInControllerGraveyardCondition(3, StaticFilters.FILTER_CARD_CREATURE);
    private static final Hint hint = new ValueHint(
            "Creature cards in your graveyard",
            new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE)
    );

    public GraveResearcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}", "Reanimate", new CardType[]{CardType.SORCERY}, "{B}");
        
        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, surveil 1. Then if there are three or more creature cards in your graveyard, this creature becomes prepared.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new SurveilEffect(1));
        ability.addEffect(new ConditionalOneShotEffect(new BecomePreparedSourceEffect(), condition,
                "Then if there are three or more creature cards in your graveyard, this creature becomes prepared."));
        this.addAbility(ability.addHint(hint));

        // Reanimate
        // Sorcery {B}
        // Put target creature card from a graveyard onto the battlefield under your control. You lose life equal to that card's mana value.
        this.getSpellCard().getSpellAbility().addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE));
        this.getSpellCard().getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("put target creature card from a graveyard onto the battlefield under your control"));
        Effect effect = new LoseLifeSourceControllerEffect(TargetManaValue.instance);
        effect.setText("You lose life equal to its mana value");
        this.getSpellCard().getSpellAbility().addEffect(effect);
    }

    private GraveResearcher(final GraveResearcher card) {
        super(card);
    }

    @Override
    public GraveResearcher copy() {
        return new GraveResearcher(this);
    }
}
