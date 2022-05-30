
package mage.cards.e;

import java.util.UUID;
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
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public final class EzuriRenegadeLeader extends CardImpl {

    private static final FilterCreaturePermanent elfFilter = new FilterCreaturePermanent("Elf creatures");
    private static final FilterControlledCreaturePermanent notEzuri = new FilterControlledCreaturePermanent();

    static {
        elfFilter.add(SubType.ELF.getPredicate());

        notEzuri.add(SubType.ELF.getPredicate());
        notEzuri.add(Predicates.not(new NamePredicate("Ezuri, Renegade Leader")));
    }

    public EzuriRenegadeLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        Ability ezuriRegen = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateTargetEffect(), new ManaCostsImpl<>("{G}"));
        TargetControlledCreaturePermanent regenTarget = new TargetControlledCreaturePermanent(1, 1, notEzuri, false);
        regenTarget.setTargetName("another target Elf");
        ezuriRegen.addTarget(regenTarget);
        this.addAbility(ezuriRegen);

        Ability ezuriBoost = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostControlledEffect(3, 3, Duration.EndOfTurn, elfFilter, false),
                new ManaCostsImpl<>("{2}{G}{G}{G}"));
        ezuriBoost.addEffect(new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, elfFilter));
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
