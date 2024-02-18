package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SurveilTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public final class BloodOperative extends CardImpl {

    public BloodOperative(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Blood Operative enters the battlefield, you may exile target card from a graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect(), true);
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);

        // Whenever you surveil, if Blood Operative is in your graveyard, you may pay 3 life. If you do, return Blood Operative to your hand.
        this.addAbility(new SurveilTriggeredAbility(Zone.GRAVEYARD, new DoIfCostPaid(
                new ReturnSourceFromGraveyardToHandEffect(), new PayLifeCost(3)
        )));
    }

    private BloodOperative(final BloodOperative card) {
        super(card);
    }

    @Override
    public BloodOperative copy() {
        return new BloodOperative(this);
    }
}
