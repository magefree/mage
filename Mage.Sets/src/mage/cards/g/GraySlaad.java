package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GraySlaad extends AdventureCard {

    private static final Condition condition
            = new CardsInControllerGraveyardCondition(4, StaticFilters.FILTER_CARD_CREATURE);

    public GraySlaad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{2}{B}", "Entropic Decay", "{1}{B}");

        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // As long as there are four or more creature cards in your graveyard, Gray Slaad has menace and deathtouch.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new MenaceAbility(false), Duration.WhileOnBattlefield),
                condition, "as long as there are four or more creature cards in your graveyard, {this} has menace"
        ));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(
                DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield
        ), condition, "and deathtouch"));

        // Entropic Decay
        // Mill four cards.
        this.getSpellCard().getSpellAbility().addEffect(new MillCardsControllerEffect(4));
    }

    private GraySlaad(final GraySlaad card) {
        super(card);
    }

    @Override
    public GraySlaad copy() {
        return new GraySlaad(this);
    }
}
