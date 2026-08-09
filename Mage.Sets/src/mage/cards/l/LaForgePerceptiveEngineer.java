package mage.cards.l;

import java.util.UUID;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.Cost;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.conditional.ManaCondition;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class LaForgePerceptiveEngineer extends CardImpl {

    public LaForgePerceptiveEngineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {T}: Add {U}. Spend this mana only to cast an artifact spell.
        this.addAbility(new ConditionalColoredManaAbility(Mana.BlueMana(1), new LaForgeSpellManaBuilder()));
    }

    private LaForgePerceptiveEngineer(final LaForgePerceptiveEngineer card) {
        super(card);
    }

    @Override
    public LaForgePerceptiveEngineer copy() {
        return new LaForgePerceptiveEngineer(this);
    }
}

class LaForgeSpellManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new LaForgeSpellConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast an artifact spell";
    }
}

class LaForgeSpellConditionalMana extends ConditionalMana {

    public LaForgeSpellConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast an artifact spell";
        addCondition(new LaForgeSpellManaCondition());
    }
}

class LaForgeSpellManaCondition extends ManaCondition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source);
        return source instanceof SpellAbility
                && !source.isActivated()
                && sourceObject != null
                && sourceObject.isArtifact(game);
    }

    @Override
    public boolean apply(Game game, Ability source, UUID originalId, Cost costToPay) {
        return apply(game, source);
    }
}
