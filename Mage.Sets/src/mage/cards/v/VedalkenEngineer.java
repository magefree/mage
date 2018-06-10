
package mage.cards.v;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
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

/**
 *
 * @author jeffwadsworth
 */
public final class VedalkenEngineer extends CardImpl {

    public VedalkenEngineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Add two mana of any one color. Spend this mana only to cast artifact spells or activate abilities of artifacts.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new VedalkenEngineerEffect(2, new VedalkenEngineerManaBuilder()), new TapSourceCost()));
    }

    public VedalkenEngineer(final VedalkenEngineer card) {
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
        MageObject object = game.getObject(source.getSourceId());
        if (object != null && object.isArtifact()) {
            return true;
        }
        return false;
    }
}

class VedalkenEngineerEffect extends ManaEffect {

    private final int amount;
    private final ConditionalManaBuilder manaBuilder;

    public VedalkenEngineerEffect(int amount, ConditionalManaBuilder manaBuilder) {
        super();
        this.amount = amount;
        this.manaBuilder = manaBuilder;
        staticText = "Add " + amount + " mana of any one color. " + manaBuilder.getRule();
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
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            checkToFirePossibleEvents(getMana(game, source), game, source);
            controller.getManaPool().addMana(getMana(game, source), game, source);
            return true;
        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ChoiceColor choiceColor = new ChoiceColor(true);
        if (controller != null && controller.choose(Outcome.Benefit, choiceColor, game)) {

            Mana condMana = manaBuilder.setMana(choiceColor.getMana(amount), source, game).build();
            return condMana;
        }
        return null;
    }

}
