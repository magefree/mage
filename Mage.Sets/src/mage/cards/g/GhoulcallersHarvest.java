package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.ZombieDecayedToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GhoulcallersHarvest extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Half the number of creature cards in your graveyard", GhoulcallersHarvestValue.instance
    );

    public GhoulcallersHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}{G}");

        // Create X 2/2 black Zombie creature tokens with decayed, where X is half the number of creature cards in your graveyard, rounded up.
        this.getSpellAbility().addEffect(new CreateTokenEffect(
                new ZombieDecayedToken(), GhoulcallersHarvestValue.instance
        ).setText("create X 2/2 black Zombie creature tokens with decayed, " +
                "where X is half the number of creature cards in your graveyard, rounded up"));
        this.getSpellAbility().addHint(hint);

        // Flashback {3}{B}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{3}{B}{G}")));
    }

    private GhoulcallersHarvest(final GhoulcallersHarvest card) {
        super(card);
    }

    @Override
    public GhoulcallersHarvest copy() {
        return new GhoulcallersHarvest(this);
    }
}

enum GhoulcallersHarvestValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player == null) {
            return 0;
        }
        int amount = player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game);
        return Math.floorDiv(amount, 2) + amount % 2;
    }

    @Override
    public GhoulcallersHarvestValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}