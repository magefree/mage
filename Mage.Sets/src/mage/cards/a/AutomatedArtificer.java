package mage.cards.a;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AutomatedArtificer extends CardImpl {

    public AutomatedArtificer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Add {C}. Spend this mana only to activate an ability or cast an artifact spell.
        this.addAbility(new ConditionalColorlessManaAbility(
                new TapSourceCost(), 1, new AutomatedArtificerManaBuilder()
        ));
    }

    private AutomatedArtificer(final AutomatedArtificer card) {
        super(card);
    }

    @Override
    public AutomatedArtificer copy() {
        return new AutomatedArtificer(this);
    }
}

class AutomatedArtificerManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new AutomatedArtificerConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to activate an ability or cast an artifact spell";
    }
}

class AutomatedArtificerConditionalMana extends ConditionalMana {

    AutomatedArtificerConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to activate an ability or cast an artifact spell";
        addCondition(new AutomatedArtificerManaCondition());
    }
}

class AutomatedArtificerManaCondition extends ManaCondition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source == null || source.isActivated()) {
            return false;
        }
        if (source.isActivatedAbility()) {
            return true;
        }
        if (source.getAbilityType() == AbilityType.SPELL) {
            if (source instanceof SpellAbility) {
                MageObject object = game.getObject(source);
                return object != null && object.isArtifact(game);
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return apply(game, source);
    }
}
