package mage.cards.a;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 * @author muz
 */
public final class AmbulatoryReactor extends CardImpl {

    public AmbulatoryReactor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.BORG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Artifact creatures you control have "{T}: Add {C}. Spend this mana only to cast an artifact spell or activate an ability of an artifact source."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            new ConditionalColorlessManaAbility(1, new AmbulatoryReactorManaBuilder()),
            Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        )));
    }

    private AmbulatoryReactor(final AmbulatoryReactor card) {
        super(card);
    }

    @Override
    public AmbulatoryReactor copy() {
        return new AmbulatoryReactor(this);
    }
}

class AmbulatoryReactorManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        ConditionalMana result = new ConditionalMana(mana);
        result.addCondition(AmbulatoryReactorCondition.instance);
        return result;
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast an artifact spell or activate an ability of an artifact source";
    }
}

enum AmbulatoryReactorCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return object != null && object.isArtifact(game);
    }
}
