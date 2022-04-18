package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.HideawayPlayEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.HideawayAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.CitizenGreenWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RabbleRousing extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_PERMANENT_CREATURE, ComparisonType.MORE_THAN, 9
    );

    public RabbleRousing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");

        // Hideaway 5
        this.addAbility(new HideawayAbility(5));

        // Whenever you attack with one or more creatures, create that many 1/1 green and white Citizen creature tokens. Then if you control ten or more creatures, you may play the exiled card without paying its mana cost.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(
                new CreateTokenEffect(new CitizenGreenWhiteToken(), RabbleRousingValue.instance)
                        .setText("create that many 1/1 green and white Citizen creature tokens"), 1
        ).setTriggerPhrase("Whenever you attack with one or more creatures, ");
        ability.addEffect(new ConditionalOneShotEffect(
                new HideawayPlayEffect(), condition, "Then if you control ten or more creatures, " +
                "you may play the exiled card without paying its mana cost"
        ));
        this.addAbility(ability.addHint(CreaturesYouControlHint.instance));
    }

    private RabbleRousing(final RabbleRousing card) {
        super(card);
    }

    @Override
    public RabbleRousing copy() {
        return new RabbleRousing(this);
    }
}

enum RabbleRousingValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return (Integer) effect.getValue("attackers");
    }

    @Override
    public RabbleRousingValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}