package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.costs.costadjusters.LegendaryCreatureCostAdjuster;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.target.common.TargetAttackingOrBlockingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EiganjoSeatOfTheEmpire extends CardImpl {

    public EiganjoSeatOfTheEmpire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.addSuperType(SuperType.LEGENDARY);

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // Channel — {2}{W}, Discard Eiganjo, Seat of the Empire: It deals 4 damage to target attacking or blocking creature. This ability costs {1} less to activate for each legendary creature you control.
        Ability ability = new ChannelAbility(
                "{2}{W}", new DamageTargetEffect(4, "it")
        );
        ability.addEffect(new InfoEffect(
                "This ability costs {1} less to activate for each legendary creature you control"
        ));
        ability.addTarget(new TargetAttackingOrBlockingCreature());
        ability.setCostAdjuster(LegendaryCreatureCostAdjuster.instance);
        this.addAbility(ability.addHint(LegendaryCreatureCostAdjuster.getHint()));
    }

    private EiganjoSeatOfTheEmpire(final EiganjoSeatOfTheEmpire card) {
        super(card);
    }

    @Override
    public EiganjoSeatOfTheEmpire copy() {
        return new EiganjoSeatOfTheEmpire(this);
    }
}
