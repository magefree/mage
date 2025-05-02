package mage.cards.e;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.DragonElementalToken;
import mage.players.Player;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author TheElk801
 */
public final class ErisRoarOfTheStorm extends CardImpl {

    public ErisRoarOfTheStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{8}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // This spell costs {2} less to cast for each different mana value among instant and sorcery cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SpellCostReductionForEachSourceEffect(
                        2, ErisRoarOfTheStormValue.instance
                ).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true).addHint(ErisRoarOfTheStormHint.instance));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Prowess
        this.addAbility(new ProwessAbility());

        // Whenever you cast your second spell each turn, create a 4/4 red Dragon Elemental creature token with flying and prowess.
        this.addAbility(new CastSecondSpellTriggeredAbility(new CreateTokenEffect(new DragonElementalToken())));
    }

    private ErisRoarOfTheStorm(final ErisRoarOfTheStorm card) {
        super(card);
    }

    @Override
    public ErisRoarOfTheStorm copy() {
        return new ErisRoarOfTheStorm(this);
    }
}

enum ErisRoarOfTheStormValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return makeStream(game, sourceAbility)
                .mapToInt(x -> 1)
                .sum();
    }

    static Stream<Integer> makeStream(Game game, Ability source) {
        return Optional
                .ofNullable(game.getPlayer(source.getControllerId()))
                .map(Player::getGraveyard)
                .map(g -> g.getCards(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY, game))
                .map(Collection::stream)
                .map(s -> s.map(MageObject::getManaValue))
                .map(Stream::distinct)
                .orElseGet(Stream::empty);
    }

    @Override
    public ErisRoarOfTheStormValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "different mana value among instant and sorcery cards in your graveyard";
    }

    @Override
    public String toString() {
        return "1";
    }
}

enum ErisRoarOfTheStormHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        Player player = game.getPlayer(ability.getControllerId());
        if (player == null) {
            return null;
        }
        List<String> values = ErisRoarOfTheStormValue
                .makeStream(game, ability)
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.toList());
        return "Different mana values among instants and sorceries in your graveyard: " + values.size()
                + (values.size() > 0 ? " (" + String.join(", ", values) + ')' : "");
    }

    @Override
    public Hint copy() {
        return this;
    }
}
