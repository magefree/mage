package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.SoldierLifelinkToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MerryWardenOfIsengard extends CardImpl {

    public MerryWardenOfIsengard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Partner with Pippin, Warden of Isengard
        this.addAbility(new PartnerWithAbility("Pippin, Warden of Isengard"));

        // Whenever one or more artifacts enter the battlefield under your control, create a 1/1 white Soldier creature token with lifelink. This ability triggers only once each turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new CreateTokenEffect(new SoldierLifelinkToken()), StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT
        ).setTriggerPhrase("Whenever one or more artifacts enter the battlefield under your control, ").setTriggersOnceEachTurn(true));
    }

    private MerryWardenOfIsengard(final MerryWardenOfIsengard card) {
        super(card);
    }

    @Override
    public MerryWardenOfIsengard copy() {
        return new MerryWardenOfIsengard(this);
    }
}
