
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.ZombieWizardToken;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class LichLordOfUnx extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Zombies you control");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public LichLordOfUnx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new ZombieWizardToken()), new ManaCostsImpl("{U}{B}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeTargetEffect(new PermanentsOnBattlefieldCount(filter)), new ManaCostsImpl("{U}{U}{B}{B}"));
        ability.addEffect(new PutLibraryIntoGraveTargetEffect(new PermanentsOnBattlefieldCount(filter, 1)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private LichLordOfUnx(final LichLordOfUnx card) {
        super(card);
    }

    @Override
    public LichLordOfUnx copy() {
        return new LichLordOfUnx(this);
    }

}
