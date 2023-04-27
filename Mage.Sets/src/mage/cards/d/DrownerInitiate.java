
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class DrownerInitiate extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a blue spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public DrownerInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a player casts a blue spell, you may pay {1}. If you do, target player puts the top two cards of their library into their graveyard.
        Ability ability = new SpellCastAllTriggeredAbility(new DoIfCostPaid(new PutLibraryIntoGraveTargetEffect(2), new ManaCostsImpl<>("{1}")), filter, false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

    }

    private DrownerInitiate(final DrownerInitiate card) {
        super(card);
    }

    @Override
    public DrownerInitiate copy() {
        return new DrownerInitiate(this);
    }
}
