package mage.cards.o;

import java.util.UUID;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CopySourceSpellEffect;
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
import mage.util.functions.RemoveTypeCopyApplier;

/**
 *
 * @author Skiwkr
 */
public final class OsgoodOperationDouble extends CardImpl {

    private static final FilterSpell filter
            = new FilterSpell("a spell from anywhere other than your hand");

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
        this.addAbility(new CastSourceTriggeredAbility(new CopySourceSpellEffect(new RemoveTypeCopyApplier(SuperType.LEGENDARY)).setText("create a token that's a copy of it")));

        // {T}: Add {C}. Spend this mana only to cast an artifact spell or activate an ability of an artifact.
        Ability ability1 = new ConditionalColoredManaAbility(new TapSourceCost(), Mana.ColorlessMana(1), new ArtifactManaBuilder() );
        this.addAbility(ability1);

        // Paradox -- Whenever you cast a spell from anywhere other than your hand, investigate.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new InvestigateEffect(), filter, false)
                .setAbilityWord(AbilityWord.PARADOX));
    }

    private OsgoodOperationDouble(final OsgoodOperationDouble card) {
        super(card);
    }

    @Override
    public OsgoodOperationDouble copy() {
        return new OsgoodOperationDouble(this);
    }
}

class ArtifactManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new ArtifactConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast an artifact spell or activate an ability of an artifact";
    }
}

class ArtifactConditionalMana extends ConditionalMana {

    ArtifactConditionalMana(Mana mana) {
        super(mana);
        addCondition(ArtifactSpellOrActivatedAbilityCondition.instance);
    }
}

enum ArtifactSpellOrActivatedAbilityCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return object != null && object.isArtifact(game) && !source.isActivated();
    }
}