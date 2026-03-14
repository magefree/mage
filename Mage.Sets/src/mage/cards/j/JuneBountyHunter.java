package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DrewTwoOrMoreCardsCondition;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ClueArtifactToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JuneBountyHunter extends CardImpl {

    public JuneBountyHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // June can't be blocked as long as you've drawn two or more cards this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(), DrewTwoOrMoreCardsCondition.instance,
                "{this} can't be blocked as long as you've drawn two or more cards this turn"
        )));

        // {1}, Sacrifice another creature: Create a Clue token. Activate only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new CreateTokenEffect(new ClueArtifactToken()), new GenericManaCost(1), MyTurnCondition.instance
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_ANOTHER_CREATURE));
        this.addAbility(ability);
    }

    private JuneBountyHunter(final JuneBountyHunter card) {
        super(card);
    }

    @Override
    public JuneBountyHunter copy() {
        return new JuneBountyHunter(this);
    }
}
