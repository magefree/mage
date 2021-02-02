
package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class NebelgastHerald extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SPIRIT, "Spirit");

    public NebelgastHerald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Nebelgast Herald or another Spirit enters the battlefield under your control, tap target creature an opponent controls.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new TapTargetEffect(), filter, false, true
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private NebelgastHerald(final NebelgastHerald card) {
        super(card);
    }

    @Override
    public NebelgastHerald copy() {
        return new NebelgastHerald(this);
    }
}
