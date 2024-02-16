package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.RevealTargetFromHandCostCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class MartyrOfSpores extends CardImpl {

    private static final FilterCard filter = new FilterCard("X green cards from your hand");
    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public MartyrOfSpores(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.HUMAN, SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}, Reveal X green cards from your hand, Sacrifice Martyr of Spores: Target creature gets +X/+X until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BoostTargetEffect(RevealTargetFromHandCostCount.instance, RevealTargetFromHandCostCount.instance, Duration.EndOfTurn),
                new GenericManaCost(1)
        );
        ability.addCost(new RevealTargetFromHandCost(new TargetCardInHand(0, Integer.MAX_VALUE, filter)));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private MartyrOfSpores(final MartyrOfSpores card) {
        super(card);
    }

    @Override
    public MartyrOfSpores copy() {
        return new MartyrOfSpores(this);
    }
}
