package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.replacement.AdditionalTriggerObjectReplacementEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.TargetPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class SplinterRadicalRat extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.NINJA, "Ninja");

    private static final FilterPermanent filter2 = new FilterControlledCreaturePermanent(SubType.NINJA);

    public SplinterRadicalRat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W/B}{W/B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.RAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // If any ability of a Ninja creature you control triggers, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new AdditionalTriggerObjectReplacementEffect(filter2)));

        // {1}{U}: Target Ninja can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(new CantBeBlockedTargetEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{1}{U}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SplinterRadicalRat(final SplinterRadicalRat card) {
        super(card);
    }

    @Override
    public SplinterRadicalRat copy() {
        return new SplinterRadicalRat(this);
    }
}
