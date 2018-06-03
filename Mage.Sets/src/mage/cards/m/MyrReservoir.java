
package mage.cards.m;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.mana.BasicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author nantuko
 */
public final class MyrReservoir extends CardImpl {

    private static final FilterCard myrCardFilter = new FilterCard("Myr card from your graveyard");

    static {
        myrCardFilter.add(new SubtypePredicate(SubType.MYR));
    }

    public MyrReservoir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {tap}: Add {C}{C}. Spend this mana only to cast Myr spells or activate abilities of Myr.
        this.addAbility(new MyrReservoirManaAbility());

        // {3}, {tap}: Return target Myr card from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(myrCardFilter));
        this.addAbility(ability);
    }

    public MyrReservoir(final MyrReservoir card) {
        super(card);
    }

    @Override
    public MyrReservoir copy() {
        return new MyrReservoir(this);
    }
}

class MyrReservoirManaAbility extends BasicManaAbility {

    MyrReservoirManaAbility() {
        super(new BasicManaEffect(new MyrConditionalMana()));
        this.netMana.add(Mana.ColorlessMana(2));
    }

    MyrReservoirManaAbility(MyrReservoirManaAbility ability) {
        super(ability);
    }

    @Override
    public MyrReservoirManaAbility copy() {
        return new MyrReservoirManaAbility(this);
    }
}

class MyrConditionalMana extends ConditionalMana {

    public MyrConditionalMana() {
        super(Mana.ColorlessMana(2));
        staticText = "Spend this mana only to cast Myr spells or activate abilities of Myr";
        addCondition(new MyrManaCondition());
    }
}

class MyrManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source.getSourceId());
        if (object != null && object.hasSubtype(SubType.MYR, game)) {
            return true;
        }
        return false;
    }
}
