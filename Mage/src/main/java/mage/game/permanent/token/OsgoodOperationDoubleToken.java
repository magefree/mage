package mage.game.permanent.token;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.game.Game;

/**
 * @author muz
 */
public final class OsgoodOperationDoubleToken extends TokenImpl {

    private static final FilterSpell filter = new FilterSpell("a spell from anywhere other than your hand");
    static {
        filter.add(Predicates.not(new CastFromZonePredicate(Zone.HAND)));
    }

    public OsgoodOperationDoubleToken() {
        super("Osgood, Operation Double", "Osgood Operation Double token");
        manaCost = new ManaCostsImpl<>("{2}{U}{U}");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.ALIEN);
        subtype.add(SubType.SHAPESHIFTER);
        power = new MageInt(2);
        toughness = new MageInt(2);

        // When you cast this spell, create a token that's a copy of it, except it isn't legendary.
        this.addAbility(new SimpleStaticAbility(
            new InfoEffect("When you cast this spell, create a token that's a copy of it, except it isn't legendary.")
        ));

        // {T}: Add {C}. Spend this mana only to cast an artifact spell or activate an ability of an artifact.
        this.addAbility(new ConditionalColorlessManaAbility(1, new OsgoodOperationDoubleManaBuilder()));

        // Paradox -- Whenever you cast a spell from anywhere other than your hand, investigate.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new InvestigateEffect(), filter, false
        ).setAbilityWord(AbilityWord.PARADOX));
    }

    private OsgoodOperationDoubleToken(final OsgoodOperationDoubleToken token) {
        super(token);
    }

    @Override
    public OsgoodOperationDoubleToken copy() {
        return new OsgoodOperationDoubleToken(this);
    }
}

class OsgoodOperationDoubleManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new OsgoodOperationDoubleConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast an artifact spell or activate an ability of an artifact source";
    }
}

class OsgoodOperationDoubleConditionalMana extends ConditionalMana {

    OsgoodOperationDoubleConditionalMana(Mana mana) {
        super(mana);
        addCondition(OsgoodOperationDoubleCondition.instance);
    }
}

enum OsgoodOperationDoubleCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return object != null && object.isArtifact(game) && !source.isActivated();
    }
}
