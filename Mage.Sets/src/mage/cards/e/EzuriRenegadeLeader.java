package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public final class EzuriRenegadeLeader extends CardImpl {

    private static final FilterCreaturePermanent elfFilter = new FilterCreaturePermanent(SubType.ELF, "Elf creatures");
    private static final FilterPermanent anotherFilter = new FilterPermanent("another target Elf");

    static {
        anotherFilter.add(SubType.ELF.getPredicate());
        anotherFilter.add(AnotherPredicate.instance);
    }

    public EzuriRenegadeLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {G}: Regenerate another target Elf.
        Ability ezuriRegen = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateTargetEffect(), new ManaCostsImpl<>("{G}"));
        ezuriRegen.addTarget(new TargetPermanent(anotherFilter));
        this.addAbility(ezuriRegen);

        // {2}{G}{G}{G}: Elf creatures you control get +3/+3 and gain trample until end of turn.
        Ability ezuriBoost = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostControlledEffect(3, 3, Duration.EndOfTurn, elfFilter, false)
                        .setText("Elf creatures you control get +3/+3"),
                new ManaCostsImpl<>("{2}{G}{G}{G}"));
        ezuriBoost.addEffect(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, elfFilter)
                .setText("and gain trample until end of turn"));
        this.addAbility(ezuriBoost);
    }

    private EzuriRenegadeLeader(final EzuriRenegadeLeader card) {
        super(card);
    }

    @Override
    public EzuriRenegadeLeader copy() {
        return new EzuriRenegadeLeader(this);
    }
}
