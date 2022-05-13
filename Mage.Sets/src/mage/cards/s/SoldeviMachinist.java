
package mage.cards.s;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
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

/**
 *
 * @author hanasu
 */
public final class SoldeviMachinist extends CardImpl {

    public SoldeviMachinist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Add {C}{C}. Spend this mana only to activate abilities of artifacts.
        this.addAbility(new ConditionalColorlessManaAbility(new TapSourceCost(), 2, new SoldeviMachinistManaBuilder()));
    }

    private SoldeviMachinist(final SoldeviMachinist card) {
        super(card);
    }

    @Override
    public SoldeviMachinist copy() {
        return new SoldeviMachinist(this);
    }
}

class SoldeviMachinistManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new ArtifactAbilityConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to activate abilities of artifacts";
    }
}

class ArtifactAbilityConditionalMana extends ConditionalMana {

    public ArtifactAbilityConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to activate abilities of artifacts";
        addCondition(new ArtifactAbilityManaCondition());
    }
}

class ArtifactAbilityManaCondition extends ManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source != null && source.getAbilityType() == AbilityType.ACTIVATED) {
            MageObject object = game.getObject(source);
            if (object != null && object.isArtifact(game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costsToPay) {
        return apply(game, source);
    }
}
