
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class TajuruArcher extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Allies you control");
    private static final FilterCreaturePermanent filterTarget = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(SubType.ALLY.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
        filterTarget.add(new AbilityPredicate(FlyingAbility.class));
    }

    public TajuruArcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);
        Ability ability = new AllyEntersBattlefieldTriggeredAbility(new DamageTargetEffect(new PermanentsOnBattlefieldCount(filter)), true);
        ability.addTarget(new TargetCreaturePermanent(filterTarget));
        this.addAbility(ability.setAbilityWord(null));
    }

    private TajuruArcher(final TajuruArcher card) {
        super(card);
    }

    @Override
    public TajuruArcher copy() {
        return new TajuruArcher(this);
    }
}
