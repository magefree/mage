package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DocksideExtortionist extends CardImpl {

    public DocksideExtortionist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Dockside Extortionist enters the battlefield, create X Treasure tokens, where X is the number of artifacts and enchantments your opponents control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new TreasureToken(), DocksideExtortionistValue.instance)
        ));
    }

    private DocksideExtortionist(final DocksideExtortionist card) {
        super(card);
    }

    @Override
    public DocksideExtortionist copy() {
        return new DocksideExtortionist(this);
    }
}

enum DocksideExtortionistValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getOpponents(sourceAbility.getControllerId())
                .stream()
                .mapToInt(uuid -> game.getBattlefield().getAllActivePermanents(
                        StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT, uuid, game
                ).size())
                .sum();
    }

    @Override
    public DocksideExtortionistValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "the number of artifacts and enchantments your opponents control";
    }

    @Override
    public String toString() {
        return "X";
    }
}