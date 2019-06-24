package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

import java.util.*;

/**
 * @author TheElk801
 */
public final class EmbodimentOfAgonies extends CardImpl {

    public EmbodimentOfAgonies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Embodiment of Agonies enters the battlefield with a +1/+1 counter on it for each different mana cost among nonland cards in your graveyard.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(), EmbodimentOfAgoniesValue.instance, false
        ), "with a +1/+1 counter on it for each different mana cost among nonland cards in your graveyard"));
    }

    private EmbodimentOfAgonies(final EmbodimentOfAgonies card) {
        super(card);
    }

    @Override
    public EmbodimentOfAgonies copy() {
        return new EmbodimentOfAgonies(this);
    }
}

enum EmbodimentOfAgoniesValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player == null) {
            return 0;
        }
        Set<String> stringSet = new HashSet();
        player.getGraveyard()
                .getCards(game)
                .stream()
                .filter(card -> !card.isLand())
                .forEach(card -> stringSet.add(getCosts(card.getManaCost())));
        stringSet.removeIf(s -> s == null || s.equals(""));
        return stringSet.size();
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }

    private static String getCosts(ManaCosts<ManaCost> costs) {
        List<String> newList = new ArrayList();
        int generic = 0;
        boolean hasGeneric = false;
        for (String s : costs.getSymbols()) {
            if (s.matches("\\{\\d*\\}")) {
                generic += Integer.parseInt(s.substring(1, s.length() - 1));
                hasGeneric = true;
            } else {
                newList.add(s);
            }
        }
        Collections.sort(newList);
        if (hasGeneric) {
            newList.add("{" + generic + "}");
        }
        return String.join("", newList);
    }
}