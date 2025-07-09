package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AtarkaPummeler extends CardImpl {

    public AtarkaPummeler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // <i>Formidable</i> &mdash; {3}{R}{R}: Creatures you control gain menace until end of turn. Activate this ability only if creature you control have total power 8 or greater.  (They can't be blocked except by two or more creatures.)
        this.addAbility(new ActivateIfConditionActivatedAbility(new GainAbilityAllEffect(
                new MenaceAbility(false), Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES
        ), new ManaCostsImpl<>("{3}{R}{R}"), FormidableCondition.instance).setAbilityWord(AbilityWord.FORMIDABLE));
    }

    private AtarkaPummeler(final AtarkaPummeler card) {
        super(card);
    }

    @Override
    public AtarkaPummeler copy() {
        return new AtarkaPummeler(this);
    }
}
