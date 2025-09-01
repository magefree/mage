package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldWithCounterEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByOneEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RelentlessXATM092 extends CardImpl {

    public RelentlessXATM092(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // This creature can't be blocked except by three or more creatures.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedByOneEffect(3)));

        // {8}: Return this card from your graveyard to the battlefield tapped with a finality counter on it.
        this.addAbility(new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToBattlefieldWithCounterEffect(
                        CounterType.FINALITY.createInstance(), true
                ), new GenericManaCost(8)
        ));
    }

    private RelentlessXATM092(final RelentlessXATM092 card) {
        super(card);
    }

    @Override
    public RelentlessXATM092 copy() {
        return new RelentlessXATM092(this);
    }
}
