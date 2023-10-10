package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class ReclusiveArtificer extends CardImpl {

    public ReclusiveArtificer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        
        // When Reclusive Artificer enters the battlefield, you may have it deal damage to target creature equal to the number of artifacts you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACTS))
                .setText("it deals damage to target creature equal to the number of artifacts you control"), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ReclusiveArtificer(final ReclusiveArtificer card) {
        super(card);
    }

    @Override
    public ReclusiveArtificer copy() {
        return new ReclusiveArtificer(this);
    }
}
