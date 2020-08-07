package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KapshoKitefins extends CardImpl {

    public KapshoKitefins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.subtype.add(SubType.FISH);

        this.color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Kapsho Kitefins or another creature enters the battlefield under your control, tap target creature an opponent controls.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new TapTargetEffect(), StaticFilters.FILTER_PERMANENT_CREATURE, false, true
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private KapshoKitefins(final KapshoKitefins card) {
        super(card);
    }

    @Override
    public KapshoKitefins copy() {
        return new KapshoKitefins(this);
    }
}
