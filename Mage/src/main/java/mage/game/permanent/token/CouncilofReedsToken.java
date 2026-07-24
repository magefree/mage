package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CastNoncreatureSpellThisTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.common.ruleModifying.LegendRuleDoesntApplyEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

/**
 * @author muz
 */
public final class CouncilofReedsToken extends TokenImpl {

    public CouncilofReedsToken() {
        super("Council of Reeds", "Council of Reeds token");
        manaCost = new ManaCostsImpl<>("{2}{U}");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        supertype.add(SuperType.LEGENDARY);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.SCIENTIST);
        subtype.add(SubType.HERO);
        power = new MageInt(2);
        toughness = new MageInt(2);

        // The "legend rule" doesn't apply to creatures you control.
        this.addAbility(new SimpleStaticAbility(new LegendRuleDoesntApplyEffect(StaticFilters.FILTER_CONTROLLED_CREATURES)));

        // At the beginning of combat on your turn, if you've cast a noncreature spell this turn, create a token that's a copy of Council of Reeds.
        Ability ability = new BeginningOfCombatTriggeredAbility(new CreateTokenCopySourceEffect())
            .withInterveningIf(CastNoncreatureSpellThisTurnCondition.instance);
        this.addAbility(ability);
    }

    private CouncilofReedsToken(final CouncilofReedsToken token) {
        super(token);
    }

    @Override
    public CouncilofReedsToken copy() {
        return new CouncilofReedsToken(this);
    }
}
