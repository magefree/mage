package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.keyword.AdaptEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Pteramander extends CardImpl {

    public Pteramander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.SALAMANDER);
        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {7}{U}: Adapt 4. This ability costs {1} less to activate for each instant and sorcery card in your graveyard.
        Ability ability = new SimpleActivatedAbility(new AdaptEffect(4).setText("Adapt 4. This ability costs {1} less to activate for each instant and sorcery card in your graveyard."), new ManaCostsImpl<>("{7}{U}"));
        ability.setCostAdjuster(PteramanderAdjuster.instance);
        this.addAbility(ability.addHint(PteramanderAdjuster.getHint()));
    }

    private Pteramander(final Pteramander card) {
        super(card);
    }

    @Override
    public Pteramander copy() {
        return new Pteramander(this);
    }
}

enum PteramanderAdjuster implements CostAdjuster {
    instance;

    private static final DynamicValue cardsCount = new CardsInControllerGraveyardCount(
            StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY
    );
    private static final Hint hint = new ValueHint("Instant and sorcery cards in your graveyard", cardsCount);

    static Hint getHint() {
        return hint;
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller != null) {
            int count = cardsCount.calculate(game, ability, null);
            CardUtil.reduceCost(ability, count);
        }
    }
}
