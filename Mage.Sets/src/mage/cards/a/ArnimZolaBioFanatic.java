package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.VillainToken;
import mage.MageInt;


/**
 *
 * @author muz
 */
public final class ArnimZolaBioFanatic extends CardImpl {

    private static final Condition condition = new CardsInControllerGraveyardCondition(2, StaticFilters.FILTER_CARD_CREATURES);
    private static final Hint hint = new ConditionHint(condition, "There are two or more creature cards in your graveyard");

    public ArnimZolaBioFanatic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SCIENTIST);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {3}{T}: Create a tapped 2/1 black Villain creature token with menace. Activate only if there are two or more creature cards in your graveyard.
        Ability ability = new ActivateIfConditionActivatedAbility(
            new CreateTokenEffect(new VillainToken(), 1, true),
            new GenericManaCost(3),
            condition
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability.addHint(hint));
    }

    private ArnimZolaBioFanatic(final ArnimZolaBioFanatic card) {
        super(card);
    }

    @Override
    public ArnimZolaBioFanatic copy() {
        return new ArnimZolaBioFanatic(this);
    }
}
