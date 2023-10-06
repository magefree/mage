
package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WaspOfTheBitterEnd extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Bolas planeswalker spell");

    static {
        filter.add(CardType.PLANESWALKER.getPredicate());
        filter.add(SubType.BOLAS.getPredicate());
    }

    public WaspOfTheBitterEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a Bolas planeswalker spell, you may sacrifice Wasp of the Bitter End. If you do, destroy target creature.
        Ability ability = new SpellCastControllerTriggeredAbility(new DoIfCostPaid(
                new DestroyTargetEffect(), new SacrificeSourceCost()
        ), filter, false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private WaspOfTheBitterEnd(final WaspOfTheBitterEnd card) {
        super(card);
    }

    @Override
    public WaspOfTheBitterEnd copy() {
        return new WaspOfTheBitterEnd(this);
    }
}
