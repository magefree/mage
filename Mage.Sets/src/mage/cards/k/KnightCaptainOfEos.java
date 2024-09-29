package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.SoldierToken;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class KnightCaptainOfEos extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SOLDIER, "a Soldier");

    public KnightCaptainOfEos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Knight-Captain of Eos enters the battlefield, create two 1/1 white Soldier creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SoldierToken(), 2), false));

        // {W}, Sacrifice a Soldier: Prevent all combat damage that would be dealt this turn.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true), new ManaCostsImpl<>("{W}"));
        ability.addCost(new SacrificeTargetCost(filter));
        this.addAbility(ability);
    }

    private KnightCaptainOfEos(final KnightCaptainOfEos card) {
        super(card);
    }

    @Override
    public KnightCaptainOfEos copy() {
        return new KnightCaptainOfEos(this);
    }
}
