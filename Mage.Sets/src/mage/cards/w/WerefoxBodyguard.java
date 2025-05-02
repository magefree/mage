package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class WerefoxBodyguard extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other target non-Fox creature");

    static {
        filter.add(Predicates.not(SubType.FOX.getPredicate()));
        filter.add(AnotherPredicate.instance);
    }

    public WerefoxBodyguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Werefox Bodyguard enters the battlefield, exile up to one other target non-Fox creature until Werefox Bodyguard leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 1, filter, false));
        this.addAbility(ability);

        // {1}{W}, Sacrifice Werefox Bodyguard: You gain 2 life.
        ability = new SimpleActivatedAbility(new GainLifeEffect(2), new ManaCostsImpl<>("{1}{W}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private WerefoxBodyguard(final WerefoxBodyguard card) {
        super(card);
    }

    @Override
    public WerefoxBodyguard copy() {
        return new WerefoxBodyguard(this);
    }
}
