package mage.cards.v;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class VedalkenEngineer extends CardImpl {

    public VedalkenEngineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Add two mana of any one color. Spend this mana only to cast artifact spells or activate abilities of artifacts.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new VedalkenEngineerEffect(2, new VedalkenEngineerManaBuilder()), new TapSourceCost()));
    }

    private VedalkenEngineer(final VedalkenEngineer card) {
        super(card);
    }

    @Override
    public VedalkenEngineer copy() {
        return new VedalkenEngineer(this);
    }
}

class VedalkenEngineerManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new VedalkenEngineerConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast artifact spells or activate abilities of artifacts";
    }
}

class VedalkenEngineerConditionalMana extends ConditionalMana {

    public VedalkenEngineerConditionalMana(Mana mana) {
        super(mana);
        addCondition(new VedalkenEngineerManaCondition());
    }
}

class VedalkenEngineerManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return object != null && object.isArtifact(game);
    }
}

class VedalkenEngineerEffect extends ManaEffect {

    private final int amount;
    private final ConditionalManaBuilder manaBuilder;

    public VedalkenEngineerEffect(int amount, ConditionalManaBuilder manaBuilder) {
        super();
        this.amount = amount;
        this.manaBuilder = manaBuilder;
        staticText = "Add " + CardUtil.numberToText(amount) + " mana of any one color. " + manaBuilder.getRule();
    }

    public VedalkenEngineerEffect(final VedalkenEngineerEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.manaBuilder = effect.manaBuilder;
    }

    @Override
    public VedalkenEngineerEffect copy() {
        return new VedalkenEngineerEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        Mana.AnyMana(amount);
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game != null) {
            Player controller = game.getPlayer(source.getControllerId());
            ChoiceColor choiceColor = new ChoiceColor(true);
            if (controller != null && controller.choose(Outcome.Benefit, choiceColor, game)) {
                return manaBuilder.setMana(choiceColor.getMana(amount), source, game).build();
            }
        }
        return new Mana();
    }

}
