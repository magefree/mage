package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.Ownerable;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HuskbursterSwarm extends CardImpl {

    public HuskbursterSwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{B}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // This spell costs {1} less to cast for each creature card you own in exile and in your graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SpellCostReductionForEachSourceEffect(1, HuskbursterSwarmValue.instance)
                        .setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true).addHint(HuskbursterSwarmValue.getHint()));

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
    }

    private HuskbursterSwarm(final HuskbursterSwarm card) {
        super(card);
    }

    @Override
    public HuskbursterSwarm copy() {
        return new HuskbursterSwarm(this);
    }
}

enum HuskbursterSwarmValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint(
            "Creature cards you own in exile and in your graveyard", instance
    );

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player == null) {
            return 0;
        }
        return game
                .getExile()
                .getCardsOwned(StaticFilters.FILTER_CARD_CREATURE, player.getId(), sourceAbility, game)
                .size()
                + player
                .getGraveyard()
                .count(StaticFilters.FILTER_CARD_CREATURE, game);
    }

    @Override
    public HuskbursterSwarmValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "creature card you own in exile and in your graveyard";
    }

    @Override
    public String toString() {
        return "1";
    }
}
