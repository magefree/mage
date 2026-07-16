package mage.cards.v;

import java.util.UUID;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.ConniveTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class VillainousHideout extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.VILLAIN);

    public VillainousHideout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");


        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add one mana of any color. Spend this mana only to cast a Villain spell or to activate an ability of a Villain source.
        this.addAbility(new ConditionalAnyColorManaAbility(
            new TapSourceCost(), 1, new VillainousHideoutManaBuilder()
        ));

        // {3}, {T}: Target Villain you control connives. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
            new ConniveTargetEffect(), new ManaCostsImpl<>("{3}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private VillainousHideout(final VillainousHideout card) {
        super(card);
    }

    @Override
    public VillainousHideout copy() {
        return new VillainousHideout(this);
    }
}

class VillainousHideoutManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new VillainousHideoutConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a Villain spell or activate an ability of a Villain source";
    }
}

class VillainousHideoutConditionalMana extends ConditionalMana {
    public VillainousHideoutConditionalMana(Mana mana) {
        super(mana);
        addCondition(VillainousHideoutCondition.instance);
    }
}

enum VillainousHideoutCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return object != null && object.hasSubtype(SubType.VILLAIN, game);
    }
}
