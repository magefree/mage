package mage.cards.m;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class MyrReservoir extends CardImpl {

    private static final FilterCard myrCardFilter = new FilterCard("Myr card from your graveyard");

    static {
        myrCardFilter.add(SubType.MYR.getPredicate());
    }

    public MyrReservoir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add {C}{C}. Spend this mana only to cast Myr spells or activate abilities of Myr.
        this.addAbility(new ConditionalColorlessManaAbility(2, new MyrReservoirManaBuilder()));

        // {3}, {T}: Return target Myr card from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(new ReturnFromGraveyardToHandTargetEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(myrCardFilter));
        this.addAbility(ability);
    }

    private MyrReservoir(final MyrReservoir card) {
        super(card);
    }

    @Override
    public MyrReservoir copy() {
        return new MyrReservoir(this);
    }
}

class MyrReservoirManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new MyrReservoirConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast Myr spells or activate abilities of Myr";
    }
}

class MyrReservoirConditionalMana extends ConditionalMana {

    MyrReservoirConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast Myr spells or activate abilities of Myr";
        addCondition(MyrReservoirManaCondition.instance);
    }
}

enum MyrReservoirManaCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        if (object != null && object.hasSubtype(SubType.MYR, game)) {
            return true;
        }
        return false;
    }
}
