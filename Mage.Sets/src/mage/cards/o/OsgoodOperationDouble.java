package mage.cards.o;

import java.util.UUID;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.util.functions.RemoveTypeCopyApplier;

/**
 * @author balazskristof
 */
public final class OsgoodOperationDouble extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell from anywhere other than your hand");

    static {
        filter.add(Predicates.not(new CastFromZonePredicate(Zone.HAND)));
    }

    public OsgoodOperationDouble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When you cast this spell, create a token that's a copy of it, except it isn't legendary.
        this.addAbility(new CastSourceTriggeredAbility(new OsgoodOperationDoubleEffect()));

        // {T}: Add {C}. Spend this mana only to cast an artifact spell or activate an ability of an artifact.
        this.addAbility(new ConditionalColoredManaAbility(Mana.ColorlessMana(1), new OsgoodOperationDoubleManaBuilder()));

        // Paradox -- Whenever you cast a spell from anywhere other than your hand, investigate.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new InvestigateEffect(),
                filter, false
        ).setAbilityWord(AbilityWord.PARADOX));
    }

    private OsgoodOperationDouble(final OsgoodOperationDouble card) {
        super(card);
    }

    @Override
    public OsgoodOperationDouble copy() {
        return new OsgoodOperationDouble(this);
    }
}

class OsgoodOperationDoubleEffect extends OneShotEffect {

    OsgoodOperationDoubleEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of it, except it isn't legendary";
    }

    protected OsgoodOperationDoubleEffect(final OsgoodOperationDoubleEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpell(source.getSourceId());
        if (spell == null) {
            return false;
        }
        spell.createCopyOnStack(game, source, source.getControllerId(), false, 1, new RemoveTypeCopyApplier(SuperType.LEGENDARY));
        return true;
    }

    @Override
    public OsgoodOperationDoubleEffect copy() { return new OsgoodOperationDoubleEffect(this); }
}

class OsgoodOperationDoubleManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new OsgoodOperationDoubleConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast an artifact spell or activate an ability of an artifact.";
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
        return object != null && object.isArtifact(game);
    }
}