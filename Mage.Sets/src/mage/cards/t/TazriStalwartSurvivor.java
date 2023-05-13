package mage.cards.t;

import mage.*;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TazriStalwartSurvivor extends CardImpl {

    public TazriStalwartSurvivor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Each creature you control has "{T}: Add one mana of any of this creature's colors. Spend this mana only to activate an ability of a creature. Activate only if this creature has another activated ability."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new TazriStalwartSurvivorManaAbility(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("each creature you control has \"{T}: Add one mana of any of this creature's colors. " +
                "Spend this mana only to activate an ability of a creature. Activate only if " +
                "this creature has another activated ability.\"")));

        // {W}{U}{B}{R}{G}, {T}: Mill five cards. Put all creature cards with activated abilities that aren't mana abilities from among the milled cards into your hand.
        Ability ability = new SimpleActivatedAbility(
                new TazriStalwartSurvivorMillEffect(), new ManaCostsImpl<>("{W}{U}{B}{R}{G}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TazriStalwartSurvivor(final TazriStalwartSurvivor card) {
        super(card);
    }

    @Override
    public TazriStalwartSurvivor copy() {
        return new TazriStalwartSurvivor(this);
    }
}

class TazriStalwartSurvivorManaAbility extends ActivatedManaAbilityImpl {

    private enum TazriStalwartSurvivorCondition implements Condition {
        instance;

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent permanent = source.getSourcePermanentIfItStillExists(game);
            return permanent != null
                    && permanent
                    .getAbilities(game)
                    .stream()
                    .filter(ability -> ability.getAbilityType() == AbilityType.ACTIVATED
                            || ability.getAbilityType() == AbilityType.MANA)
                    .map(Ability::getOriginalId)
                    .anyMatch(abilityId -> !source.getOriginalId().equals(abilityId));
        }
    }

    TazriStalwartSurvivorManaAbility() {
        super(Zone.BATTLEFIELD, new TazriStalwartSurvivorManaEffect(), new TapSourceCost());
        this.condition = TazriStalwartSurvivorCondition.instance;
    }

    private TazriStalwartSurvivorManaAbility(final TazriStalwartSurvivorManaAbility ability) {
        super(ability);
    }

    @Override
    public TazriStalwartSurvivorManaAbility copy() {
        return new TazriStalwartSurvivorManaAbility(this);
    }

    @Override
    public String getRule() {
        return "{T}: Add one mana of any of this creature's colors. " +
                "Spend this mana only to activate an ability of a creature. " +
                "Activate only if this creature has another activated ability.";
    }
}

class TazriStalwartSurvivorManaEffect extends ManaEffect {

    private static final class TazriStalwartSurvivorConditionalMana extends ConditionalMana {

        public TazriStalwartSurvivorConditionalMana(Mana mana) {
            super(mana);
            staticText = "Spend this mana only to activate abilities of creatures";
            addCondition(new TazriStalwartSurvivorManaCondition());
        }
    }

    private static final class TazriStalwartSurvivorManaCondition extends ManaCondition implements Condition {

        @Override
        public boolean apply(Game game, Ability source) {
            switch (source.getAbilityType()) {
                case ACTIVATED:
                case MANA:
                    break;
                default:
                    return false;
            }
            MageObject object = game.getObject(source);
            return object != null && object.isCreature(game);
        }

        @Override
        public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
            return apply(game, source);
        }
    }

    public TazriStalwartSurvivorManaEffect() {
        super();
        staticText = "Add one mana of any of this creature's colors. " +
                "Spend this mana only to activate an ability of a creature";
    }

    private TazriStalwartSurvivorManaEffect(final TazriStalwartSurvivorManaEffect effect) {
        super(effect);
    }

    @Override
    public TazriStalwartSurvivorManaEffect copy() {
        return new TazriStalwartSurvivorManaEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        if (game == null) {
            return new ArrayList<>();
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return new ArrayList<>();
        }
        List<Mana> netMana = new ArrayList<>();
        ObjectColor color = permanent.getColor(game);
        if (color.isWhite()) {
            netMana.add(new TazriStalwartSurvivorConditionalMana(Mana.WhiteMana(1)));
        }
        if (color.isBlue()) {
            netMana.add(new TazriStalwartSurvivorConditionalMana(Mana.BlueMana(1)));
        }
        if (color.isBlack()) {
            netMana.add(new TazriStalwartSurvivorConditionalMana(Mana.BlackMana(1)));
        }
        if (color.isRed()) {
            netMana.add(new TazriStalwartSurvivorConditionalMana(Mana.RedMana(1)));
        }
        if (color.isGreen()) {
            netMana.add(new TazriStalwartSurvivorConditionalMana(Mana.GreenMana(1)));
        }
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game == null) {
            return new Mana();
        }
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (controller == null || permanent == null) {
            return new Mana();
        }
        Choice choice = new ChoiceImpl();
        choice.setMessage("Pick a mana color");
        ObjectColor color = permanent.getColor(game);
        if (color.isWhite()) {
            choice.getChoices().add("White");
        }
        if (color.isBlue()) {
            choice.getChoices().add("Blue");
        }
        if (color.isBlack()) {
            choice.getChoices().add("Black");
        }
        if (color.isRed()) {
            choice.getChoices().add("Red");
        }
        if (color.isGreen()) {
            choice.getChoices().add("Green");
        }
        if (choice.getChoices().isEmpty()) {
            return new Mana();
        }
        if (choice.getChoices().size() == 1) {
            choice.setChoice(choice.getChoices().iterator().next());
        } else {
            controller.choose(outcome, choice, game);
        }
        if (choice.getChoice() == null) {
            return new Mana();
        }
        switch (choice.getChoice()) {
            case "White":
                return new TazriStalwartSurvivorConditionalMana(Mana.WhiteMana(1));
            case "Blue":
                return new TazriStalwartSurvivorConditionalMana(Mana.BlueMana(1));
            case "Black":
                return new TazriStalwartSurvivorConditionalMana(Mana.BlackMana(1));
            case "Red":
                return new TazriStalwartSurvivorConditionalMana(Mana.RedMana(1));
            case "Green":
                return new TazriStalwartSurvivorConditionalMana(Mana.GreenMana(1));
            default:
                return new Mana();
        }
    }

}

class TazriStalwartSurvivorMillEffect extends OneShotEffect {

    TazriStalwartSurvivorMillEffect() {
        super(Outcome.Benefit);
        staticText = "mill five cards. Put all creature cards with activated abilities " +
                "that aren't mana abilities from among the milled cards into your hand";
    }

    private TazriStalwartSurvivorMillEffect(final TazriStalwartSurvivorMillEffect effect) {
        super(effect);
    }

    @Override
    public TazriStalwartSurvivorMillEffect copy() {
        return new TazriStalwartSurvivorMillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = player.millCards(5, source, game);
        cards.removeIf(uuid -> !game.getCard(uuid).isCreature(game));
        cards.removeIf(uuid -> game
                .getCard(uuid)
                .getAbilities(game)
                .stream()
                .map(Ability::getAbilityType)
                .noneMatch(AbilityType.ACTIVATED::equals));
        player.moveCards(cards, Zone.HAND, source, game);
        return true;
    }
}
