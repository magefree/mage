package mage.cards.y;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YurlokOfScorchThrash extends CardImpl {

    public YurlokOfScorchThrash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // A player losing unspent mana causes that player to lose that much life.
        this.addAbility(new SimpleStaticAbility(new YurlokOfScorchThrashRuleEffect()));

        // {1}, {T}: Each player adds {B}{R}{G}.
        Ability ability = new SimpleManaAbility(
                Zone.BATTLEFIELD, new YurlokOfScorchThrashManaEffect(), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private YurlokOfScorchThrash(final YurlokOfScorchThrash card) {
        super(card);
    }

    @Override
    public YurlokOfScorchThrash copy() {
        return new YurlokOfScorchThrash(this);
    }
}

class YurlokOfScorchThrashRuleEffect extends ContinuousEffectImpl {

    YurlokOfScorchThrashRuleEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "A player losing unspent mana causes that player to lose that much life.";
    }

    private YurlokOfScorchThrashRuleEffect(final YurlokOfScorchThrashRuleEffect effect) {
        super(effect);
    }

    @Override
    public YurlokOfScorchThrashRuleEffect copy() {
        return new YurlokOfScorchThrashRuleEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        game.getState().setManaBurn(true);
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}

class YurlokOfScorchThrashManaEffect extends ManaEffect {

    YurlokOfScorchThrashManaEffect() {
        super();
        staticText = "each player adds {B}{R}{G}";
    }

    private YurlokOfScorchThrashManaEffect(final YurlokOfScorchThrashManaEffect effect) {
        super(effect);
    }

    @Override
    public YurlokOfScorchThrashManaEffect copy() {
        return new YurlokOfScorchThrashManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            Mana manaToAdd = produceMana(game, source);
            if (manaToAdd == null || manaToAdd.count() <= 0) {
                continue;
            }
            checkToFirePossibleEvents(manaToAdd, game, source);
            addManaToPool(player, manaToAdd, game, source);
        }
        return true;
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        netMana.add(new Mana(0, 0, 1, 1, 1, 0, 0, 0));
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        return new Mana(0, 0, 1, 1, 1, 0, 0, 0);
    }
}
