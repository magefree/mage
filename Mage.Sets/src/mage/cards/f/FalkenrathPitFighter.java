package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.OpponentsLostLifeCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.OpponentsLostLifeHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FalkenrathPitFighter extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.VAMPIRE);

    public FalkenrathPitFighter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {1}{R}, Discard a card, Sacrifice a Vampire: Draw two cards. Activate only if an opponent lost life this turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(2),
                new ManaCostsImpl<>("{1}{R}"), OpponentsLostLifeCondition.instance
        );
        ability.addCost(new DiscardCardCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability.addHint(OpponentsLostLifeHint.instance));
    }

    private FalkenrathPitFighter(final FalkenrathPitFighter card) {
        super(card);
    }

    @Override
    public FalkenrathPitFighter copy() {
        return new FalkenrathPitFighter(this);
    }
}
