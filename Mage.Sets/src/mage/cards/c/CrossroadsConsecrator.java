package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterAttackingCreature;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CrossroadsConsecrator extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("attacking Human");

    static {
        filter.add(SubType.HUMAN.getPredicate());
    }

    public CrossroadsConsecrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {G}, {T}: Target attacking Human gets +1/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(1, 1, Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.G));
        ability.addTarget(new TargetPermanent(filter));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private CrossroadsConsecrator(final CrossroadsConsecrator card) {
        super(card);
    }

    @Override
    public CrossroadsConsecrator copy() {
        return new CrossroadsConsecrator(this);
    }
}
