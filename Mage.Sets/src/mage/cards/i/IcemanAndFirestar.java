package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class IcemanAndFirestar extends CardImpl {

    private static final FilterSpell filterBlue = new FilterSpell("a blue spell");
    private static final FilterSpell filterRed = new FilterSpell("a red spell");

    static {
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
        filterRed.add(new ColorPredicate(ObjectColor.RED));
    }
    public IcemanAndFirestar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U/R}{U/R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a blue spell, tap up to one target creature.
        Ability ability = new SpellCastControllerTriggeredAbility(
            new TapTargetEffect(), filterBlue, false
        );
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // Whenever you cast a red spell, you may discard a card. If you do, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost()),
            filterRed, false
        ));
    }

    private IcemanAndFirestar(final IcemanAndFirestar card) {
        super(card);
    }

    @Override
    public IcemanAndFirestar copy() {
        return new IcemanAndFirestar(this);
    }
}
