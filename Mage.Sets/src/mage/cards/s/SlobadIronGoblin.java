package mage.cards.s;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SlobadIronGoblin extends CardImpl {

    public SlobadIronGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {T}, Sacrifice an artifact: Add an amount of {R} equal to the sacrificed artifact's mana value. Spend this mana only to cast artifact spells or activate abilities of artifacts.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new SlobadIronGoblinManaEffect(), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN));
        this.addAbility(ability);
    }

    private SlobadIronGoblin(final SlobadIronGoblin card) {
        super(card);
    }

    @Override
    public SlobadIronGoblin copy() {
        return new SlobadIronGoblin(this);
    }
}

class SlobadIronGoblinManaEffect extends ManaEffect {

    private final ConditionalManaBuilder manaBuilder = new SlobadIronGoblinManaBuilder();

    SlobadIronGoblinManaEffect() {
        this.staticText = "Add an amount of {R} equal to the sacrificed artifact's mana value. " +
                "Spend this mana only to cast artifact spells or activate abilities of artifacts";
    }

    private SlobadIronGoblinManaEffect(final SlobadIronGoblinManaEffect effect) {
        super(effect);
    }

    @Override
    public SlobadIronGoblinManaEffect copy() {
        return new SlobadIronGoblinManaEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        if (game == null) {
            return new ArrayList<>();
        }
        List<Mana> netMana = new ArrayList<>();
        int cmc = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT,
                        source.getControllerId(), source, game
                ).stream()
                .mapToInt(MageObject::getManaValue)
                .max()
                .orElse(-1);
        if (cmc > 0) {
            netMana.add(manaBuilder.setMana(Mana.RedMana(cmc), source, game).build());
        }
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game == null) {
            return new Mana();
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return new Mana();
        }
        int value = CardUtil
                .castStream(source.getCosts().stream(), SacrificeTargetCost.class)
                .map(SacrificeTargetCost::getPermanents)
                .flatMap(Collection::stream)
                .mapToInt(MageObject::getManaValue)
                .sum();
        return manaBuilder.setMana(Mana.RedMana(value), source, game).build();
    }
}

class SlobadIronGoblinManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new SlobadIronGoblinConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast artifact spells or activate abilities of artifacts";
    }
}

class SlobadIronGoblinConditionalMana extends ConditionalMana {

    SlobadIronGoblinConditionalMana(Mana mana) {
        super(mana);
        addCondition(SlobadIronGoblinCondition.instance);
    }
}

enum SlobadIronGoblinCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return object != null && object.isArtifact(game);
    }
}
