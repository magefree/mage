package mage.cards.g;

import java.util.UUID;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;

/**
 *
 * @author Jmlundeen
 */
public final class GuidelightOptimizer extends CardImpl {

    public GuidelightOptimizer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");
        
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {T}: Add {U}. Spend this mana only to cast an artifact spell or activate an ability.
        this.addAbility(new ConditionalColoredManaAbility(Mana.BlueMana(1), new ArtifactOrActivatedManaBuilder()));
    }

    private GuidelightOptimizer(final GuidelightOptimizer card) {
        super(card);
    }

    @Override
    public GuidelightOptimizer copy() {
        return new GuidelightOptimizer(this);
    }
}

class ArtifactOrActivatedManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new ArtifactOrActivatedConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast an artifact spell or activate an ability";
    }
}

class ArtifactOrActivatedConditionalMana extends ConditionalMana {

    public ArtifactOrActivatedConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast an artifact spell or activate an ability";
        addCondition(new ArtifactOrActivatedManaCondition());
    }
}

class ArtifactOrActivatedManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source);
        return source != null
                && (source.isActivatedAbility() && !source.isActivated())
                || (sourceObject != null && sourceObject.isArtifact());
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, mage.abilities.costs.Cost costsToPay) {
        return apply(game, source);
    }
}
