package mage.cards.m;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayFromTopOfLibraryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MmmenonTheRightHand extends CardImpl {

    private static final FilterCard filter = new FilterArtifactCard("cast artifact spells");

    public MmmenonTheRightHand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.JELLYFISH);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may cast artifact spells from the top of your library.
        this.addAbility(new SimpleStaticAbility(new PlayFromTopOfLibraryEffect(filter)));

        // Artifacts you control have "{T}: Add {U}. Spend this mana only to cast a spell from anywhere other than your hand."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new ConditionalColoredManaAbility(Mana.BlueMana(1), new MmmenonTheRightHandManaBuilder()),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_ARTIFACTS
        )));
    }

    private MmmenonTheRightHand(final MmmenonTheRightHand card) {
        super(card);
    }

    @Override
    public MmmenonTheRightHand copy() {
        return new MmmenonTheRightHand(this);
    }
}

class MmmenonTheRightHandManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new MmmenonTheRightHandConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a spell from anywhere other than your hand";
    }
}

class MmmenonTheRightHandConditionalMana extends ConditionalMana {

    public MmmenonTheRightHandConditionalMana(Mana mana) {
        super(mana);
        this.staticText = "Spend this mana only to cast a spell from anywhere other than your hand";
        addCondition(MmmenonTheRightHandManaCondition.instance);
    }
}

enum MmmenonTheRightHandManaCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        if (!(source instanceof SpellAbility)) {
            return false;
        }
        MageObject object = game.getObject(source);
        if (!source.isControlledBy(game.getOwnerId(object))) {
            return false;
        }
        if (object instanceof Spell) {
            return !Zone.HAND.match(((Spell) object).getFromZone());
        }
        // checking mana without real cast
        return game.inCheckPlayableState() && !Zone.HAND.match(game.getState().getZone(source.getSourceId()));
    }
}
