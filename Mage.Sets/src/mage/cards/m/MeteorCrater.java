package mage.cards.m;

import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.constants.CardType;
import mage.constants.ManaType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.*;

/**
 * @author anonymous
 */
public final class MeteorCrater extends CardImpl {

    public MeteorCrater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Choose a color of a permanent you control. Add one mana of that color.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new MeteorCraterEffect(), new TapSourceCost()));
    }

    private MeteorCrater(final MeteorCrater card) {
        super(card);
    }

    @Override
    public MeteorCrater copy() {
        return new MeteorCrater(this);
    }
}

class MeteorCraterEffect extends ManaEffect {

    /**
     * 04/10/2004 You can't choose "colorless". You have to choose one of the
     * five colors.
     */

    public MeteorCraterEffect() {
        super();
        staticText = "Choose a color of a permanent you control. Add one mana of that color";
    }

    public MeteorCraterEffect(final MeteorCraterEffect effect) {
        super(effect);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        return game == null ? new ArrayList<>() : ManaType.getManaListFromManaTypes(getManaTypes(game, source), true);
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game == null) {
            return null;
        }

        Set<ManaType> types = getManaTypes(game, source);
        if (types.isEmpty()) {
            return null;
        }
        Choice choice = ManaType.getChoiceOfManaTypes(types, true);
        if (choice.getChoices().size() == 1) {
            choice.setChoice(choice.getChoices().iterator().next());
        } else {
            Player player = game.getPlayer(source.getControllerId());
            if (player == null || !player.choose(outcome, choice, game)) {
                return null;
            }
        }
        ManaType chosenType = ManaType.findByName(choice.getChoice());
        return chosenType == null ? null : new Mana(chosenType);
    }

    private Set<ManaType> getManaTypes(Game game, Ability source) {
        Set<ManaType> types = new HashSet<>(5);
        if (game == null) {
            return types;
        }

        List<Permanent> controlledPermanents = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_CONTROLLED_PERMANENT, source.getControllerId(), game);
        for (Permanent permanent : controlledPermanents) {
            ObjectColor color = permanent.getColor(game);
            if (color.isBlack()) {
                types.add(ManaType.BLACK);
            }
            if (color.isBlue()) {
                types.add(ManaType.BLUE);
            }
            if (color.isGreen()) {
                types.add(ManaType.GREEN);
            }
            if (color.isRed()) {
                types.add(ManaType.RED);
            }
            if (color.isWhite()) {
                types.add(ManaType.WHITE);
            }

            // If all types are already added, exit early
            if (types.size() == 5) {
                break;
            }
        }
        return types;
    }

    @Override
    public MeteorCraterEffect copy() {
        return new MeteorCraterEffect(this);
    }
}
