package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class DuskwatchRecruiter extends TransformingDoubleFacedCard {

    public DuskwatchRecruiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WARRIOR, SubType.WEREWOLF}, "{1}{G}",
                "Krallenhorde Howler",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "G");

        // Duskwatch Recruiter
        this.getLeftHalfCard().setPT(2, 2);

        // {2}{G}: Look at the top three cards of your library. You may reveal a creature card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        Ability ability = new SimpleActivatedAbility(
                new LookLibraryAndPickControllerEffect(3, 1, StaticFilters.FILTER_CARD_CREATURE_A, PutCards.HAND, PutCards.BOTTOM_ANY),
                new ManaCostsImpl<>("{2}{G}")
        );
        this.getLeftHalfCard().addAbility(ability);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Duskwatch Recruiter.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Krallenhorde Howler
        this.getRightHalfCard().setPT(3, 3);

        // Creature spells you cast cost {1} less to cast.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(
                new SpellsCostReductionControllerEffect(new FilterCreatureCard("creature spells"), 1)
        ));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Krallenhorde Howler.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private DuskwatchRecruiter(final DuskwatchRecruiter card) {
        super(card);
    }

    @Override
    public DuskwatchRecruiter copy() {
        return new DuskwatchRecruiter(this);
    }
}
