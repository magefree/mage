package mage.cards.g;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuildGlobe extends CardImpl {

    public GuildGlobe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // When Guild Globe enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // {2}, {T}, Sacrifice Guild Globe: Add two mana of different colors.
        Ability ability = new SimpleManaAbility(
                Zone.BATTLEFIELD, new GuildGlobeManaEffect(), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private GuildGlobe(final GuildGlobe card) {
        super(card);
    }

    @Override
    public GuildGlobe copy() {
        return new GuildGlobe(this);
    }
}

class GuildGlobeManaEffect extends ManaEffect {

    GuildGlobeManaEffect() {
        super();
        staticText = "Add two mana of different colors.";
    }

    private GuildGlobeManaEffect(final GuildGlobeManaEffect effect) {
        super(effect);
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return null;
        }

        ChoiceColor color1 = new ChoiceColor(true, "Choose color 1");
        if (!player.choose(outcome, color1, game) || color1.getColor() == null) {
            return null;
        }

        ChoiceColor color2 = new ChoiceColor(true, "Choose color 2");
        color2.removeColorFromChoices(color1.getChoice());
        if (!player.choose(outcome, color2, game) || color2.getColor() == null) {
            return null;
        }

        if (color1.getColor().equals(color2.getColor())) {
            game.informPlayers("Player " + player.getName() + " is cheating with mana choices.");
            return null;
        }

        Mana mana = new Mana();
        mana.add(color1.getMana(1));
        mana.add(color2.getMana(1));
        return mana;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            checkToFirePossibleEvents(getMana(game, source), game, source);
            player.getManaPool().addMana(getMana(game, source), game, source);
            return true;
        }
        return false;
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        ArrayList<Mana> netMana = new ArrayList<>();
        netMana.add(new Mana(0, 0, 0, 0, 0, 0, 2, 0));
        return netMana;
    }

    @Override
    public GuildGlobeManaEffect copy() {
        return new GuildGlobeManaEffect(this);
    }
}