package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.HumanSoldierToken;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LossarnachCaptain extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.HUMAN, "Human");

    public LossarnachCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever Lossarnach Captain or another Human enters the battlefield under your control, tap target creature an opponent controls.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new TapTargetEffect(), filter, false, true
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // At the beginning of your upkeep, create a 1/1 white Human Soldier creature token.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new CreateTokenEffect(new HumanSoldierToken()), TargetController.YOU, false
        ));
    }

    private LossarnachCaptain(final LossarnachCaptain card) {
        super(card);
    }

    @Override
    public LossarnachCaptain copy() {
        return new LossarnachCaptain(this);
    }
}
