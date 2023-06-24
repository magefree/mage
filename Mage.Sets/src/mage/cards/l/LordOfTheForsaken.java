package mage.cards.l;

import mage.ConditionalMana;
import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LordOfTheForsaken extends CardImpl {

    public LordOfTheForsaken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // {B}, Sacrifice another creature: Target player mills three cards.
        Ability ability = new SimpleActivatedAbility(
                new MillCardsTargetEffect(3), new ManaCostsImpl<>("{B}")
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        )));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Pay 1 life: Add {C}. Spend this mana only to cast a spell from your graveyard.
        this.addAbility(new ConditionalColorlessManaAbility(
                new PayLifeCost(1), 1, new LordOfTheForsakenManaBuilder()
        ));
    }

    private LordOfTheForsaken(final LordOfTheForsaken card) {
        super(card);
    }

    @Override
    public LordOfTheForsaken copy() {
        return new LordOfTheForsaken(this);
    }
}

class LordOfTheForsakenManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new LordOfTheForsakenConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a spell from your graveyard";
    }
}

class LordOfTheForsakenConditionalMana extends ConditionalMana {

    public LordOfTheForsakenConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast a spell from your graveyard";
        addCondition(LordOfTheForsakenManaCondition.instance);
    }
}

enum LordOfTheForsakenManaCondition implements Condition {
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
            return ((Spell) object).getFromZone() == Zone.GRAVEYARD;
        }
        // checking mana without real cast
        return game.inCheckPlayableState() && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD;
    }
}
