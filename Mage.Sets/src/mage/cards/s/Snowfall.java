package mage.cards.s;

import java.util.*;

import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author Cguy7777
 */
public final class Snowfall extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent(SubType.ISLAND, "an Island is tapped");

    public Snowfall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Cumulative upkeep {U}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{U}")));

        // Whenever an Island is tapped for mana, its controller may add an additional {U}.
        // If that Island is snow, its controller may add an additional {U}{U} instead.
        // Spend this mana only to pay cumulative upkeep costs.
        this.addAbility(new TapForManaAllTriggeredManaAbility(new SnowfallManaEffect(), filter, SetTargetPointer.PERMANENT));
    }

    private Snowfall(final Snowfall card) {
        super(card);
    }

    @Override
    public Snowfall copy() {
        return new Snowfall(this);
    }
}

class SnowfallManaEffect extends ManaEffect {

    private static final Set<String> choice = new HashSet<>();

    static {
        choice.add("Add one additional blue mana.");
        choice.add("Add two additional blue mana.");
        choice.add("Don't add additional mana.");
    }

    SnowfallManaEffect() {
        super();
        this.staticText = "its controller may add an additional {U}. " +
                "If that Island is snow, its controller may add an additional {U}{U} instead. " +
                "Spend this mana only to pay cumulative upkeep costs.";
    }

    private SnowfallManaEffect(final SnowfallManaEffect effect) {
        super(effect);
    }

    @Override
    public Player getPlayer(Game game, Ability source) {
        Permanent land = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (land != null) {
            return game.getPlayer(land.getControllerId());
        }
        return null;
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        if (game == null) {
            return netMana;
        }

        Permanent land = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (land == null) {
            return netMana;
        }

        Mana mana = Mana.BlueMana(land.isSnow(game) ? 2 : 1);
        netMana.add(mana);
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game == null) {
            return new Mana();
        }

        Permanent land = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (land == null) {
            return new Mana();
        }
        Player player = game.getPlayer(land.getControllerId());
        if (player == null) {
            return new Mana();
        }

        if (land.isSnow(game)) {
            Choice choiceImpl = new ChoiceImpl();
            choiceImpl.setMessage("Add additional blue mana? This mana can only be spent to pay cumulative upkeep costs.");
            choiceImpl.setChoices(choice);

            if (player.choose(outcome, choiceImpl, game)) {
                switch (choiceImpl.getChoice()) {
                    case "Add one additional blue mana.":
                        return new SnowfallConditionalMana(Mana.BlueMana(1));
                    case "Add two additional blue mana.":
                        return new SnowfallConditionalMana(Mana.BlueMana(2));
                    default:
                        break;
                }
            }
        } else {
            String message = "Add an additional {U}? This mana can only be spent to pay cumulative upkeep costs.";
            if (player.chooseUse(outcome, message, source, game)) {
                return new SnowfallConditionalMana(Mana.BlueMana(1));
            }
        }

        return new Mana();
    }

    @Override
    public SnowfallManaEffect copy() {
        return new SnowfallManaEffect(this);
    }
}

class SnowfallConditionalMana extends ConditionalMana {

    public SnowfallConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to pay cumulative upkeep costs";
        addCondition(new SnowfallManaCondition());
    }
}

class SnowfallManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source != null) {
            return source instanceof CumulativeUpkeepAbility;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        return apply(game, source);
    }
}
