package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class MtendaGriffin extends CardImpl {

    private static final FilterCard filter = new FilterCard("Griffin card from your graveyard");

    static {
        filter.add(SubType.GRIFFIN.getPredicate());
    }

    public MtendaGriffin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.GRIFFIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {W}, {tap}: Return Mtenda Griffin to its owner's hand and return target Griffin card from your graveyard to your hand. Activate this ability only during your upkeep.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new ReturnToHandSourceEffect(true),
                new ManaCostsImpl<>("{W}"), IsStepCondition.getMyUpkeep()
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new ReturnFromGraveyardToHandTargetEffect().concatBy("and"));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private MtendaGriffin(final MtendaGriffin card) {
        super(card);
    }

    @Override
    public MtendaGriffin copy() {
        return new MtendaGriffin(this);
    }
}
