package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.OpponentDealtNoncombatDamageCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldWithCounterEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.watchers.common.NoncombatDamageToPlayersWatcher;

import java.util.UUID;

/**
 * @author muz
 */
public final class GrimRepriser extends CardImpl {

    public GrimRepriser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Prowess
        this.addAbility(new ProwessAbility());

        // {B}{R}: Return this card from your graveyard to the battlefield with a finality counter on it. Activate only if an opponent has been dealt noncombat damage this turn.
        this.addAbility(new ActivateIfConditionActivatedAbility(
            Zone.GRAVEYARD,
            new ReturnSourceFromGraveyardToBattlefieldWithCounterEffect(CounterType.FINALITY.createInstance(), false)
                    .setText("return this card from your graveyard to the battlefield with a finality counter on it"),
            new ManaCostsImpl<>("{B}{R}"), OpponentDealtNoncombatDamageCondition.THIS_TURN
        ), new NoncombatDamageToPlayersWatcher());
    }

    private GrimRepriser(final GrimRepriser card) {
        super(card);
    }

    @Override
    public GrimRepriser copy() {
        return new GrimRepriser(this);
    }
}
