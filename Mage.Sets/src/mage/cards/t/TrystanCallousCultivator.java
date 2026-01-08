package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.common.TransformsOrEntersTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.*;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TrystanCallousCultivator extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterCard(SubType.ELF);
    private static final Condition condition = new CardsInControllerGraveyardCondition(1, filter);
    private static final Hint hint = new ConditionHint(condition, "There is an Elf card in your graveyard");

    public TrystanCallousCultivator(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELF, SubType.DRUID}, "{2}{G}",
                "Trystan, Penitent Culler",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELF, SubType.WARLOCK}, "B"
        );
        this.getLeftHalfCard().setPT(3, 4);
        this.getRightHalfCard().setPT(3, 4);

        // Deathtouch
        this.getLeftHalfCard().addAbility(DeathtouchAbility.getInstance());

        // Whenever this creature enters or transforms into Trystan, Callous Cultivator, mill three cards. Then if there is an Elf card in your graveyard, you gain 2 life.
        Ability ability = new TransformsOrEntersTriggeredAbility(
                new MillCardsControllerEffect(3), false
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new GainLifeEffect(2), condition, "Then if there is " +
                "an Elf card in your graveyard, you gain 2 life"
        ));
        this.getLeftHalfCard().addAbility(ability.addHint(hint));

        // At the beginning of your first main phase, you may pay {B}. If you do, transform Trystan.
        this.getLeftHalfCard().addAbility(new BeginningOfFirstMainTriggeredAbility(
                new DoIfCostPaid(new TransformSourceEffect(), new ManaCostsImpl<>("{B}"))
        ));

        // Deathtouch
        this.getRightHalfCard().addAbility(DeathtouchAbility.getInstance());

        // Trystan, Penitent Culler
        // Whenever this creature transforms into Trystan, Penitent Culler, mill three cards, then you may exile an Elf card from your graveyard. If you do, each opponent loses 2 life.
        ability = new TransformIntoSourceTriggeredAbility(new MillCardsControllerEffect(3));
        ability.addEffect(new DoIfCostPaid(
                new LoseLifeOpponentsEffect(2),
                new ExileFromGraveCost(new TargetCardInYourGraveyard(filter))
        ));
        this.getRightHalfCard().addAbility(ability);

        // At the beginning of your first main phase, you may pay {G}. If you do, transform Trystan.
        this.getRightHalfCard().addAbility(new BeginningOfFirstMainTriggeredAbility(
                new DoIfCostPaid(new TransformSourceEffect(), new ManaCostsImpl<>("{G}"))
        ));
    }

    private TrystanCallousCultivator(final TrystanCallousCultivator card) {
        super(card);
    }

    @Override
    public TrystanCallousCultivator copy() {
        return new TrystanCallousCultivator(this);
    }
}
