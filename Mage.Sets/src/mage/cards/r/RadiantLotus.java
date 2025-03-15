package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Jmlundeen
 */
public final class RadiantLotus extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledArtifactPermanent("artifacts");
    public RadiantLotus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");
        

        // {T}, Sacrifice one or more artifacts: Choose a color. Target player adds three mana of the chosen color for each artifact sacrificed this way.
        Ability ability = new SimpleActivatedAbility(new RadiantLotusEffect(), new TapSourceCost());
        ability.addCost(new SacrificeXTargetCost(filter, true, 1).setText("Sacrifice one or more artifacts"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private RadiantLotus(final RadiantLotus card) {
        super(card);
    }

    @Override
    public RadiantLotus copy() {
        return new RadiantLotus(this);
    }
}


class RadiantLotusEffect extends OneShotEffect {

    public RadiantLotusEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "Choose a color. Target player adds three mana of the chosen color for each artifact sacrificed this way";
    }

    public RadiantLotusEffect(final RadiantLotusEffect effect) {
        super(effect);
    }

    @Override
    public RadiantLotusEffect copy() {
        return new RadiantLotusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int manaCount = source.getCosts().stream()
            .filter(SacrificeTargetCost.class::isInstance)
            .mapToInt(cost -> ((SacrificeTargetCost) cost).getPermanents().size() * 3)
            .sum();

        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            String mes = String.format("Select a color of mana to add %d of it", manaCount);
            ChoiceColor choice = new ChoiceColor(true, mes, game.getObject(source));
            if (controller.choose(outcome, choice, game)) {
                Player player = game.getPlayer(source.getFirstTarget());
                if (choice.getColor() != null && player != null) {
                    player.getManaPool().addMana(choice.getMana(manaCount), game, source);
                    return true;
                }
            }
        }
        return false;
    }
}