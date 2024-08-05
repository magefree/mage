package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class PersistentMarshstalker extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("other Rat you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AnotherPredicate.instance);
        filter.add(SubType.RAT.getPredicate());
    }

    public PersistentMarshstalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Persistent Marshstalker gets +1/+0 for each other Rat you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(new PermanentsOnBattlefieldCount(filter), StaticValue.get(0), Duration.WhileOnBattlefield)));

        // Threshold -- Whenever you attack with one or more Rats, if seven or more cards are in your graveyard, you may pay {2}{B}. If you do, return Persistent Marshstalker from your graveyard to the battlefield tapped and attacking.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(Zone.GRAVEYARD, new ConditionalOneShotEffect(new DoIfCostPaid(
                new ReturnSourceFromGraveyardToBattlefieldEffect(true, false, false, true), new ManaCostsImpl<>("{2}{B}")),
                ThresholdCondition.instance), 1, filter).setTriggerPhrase("Whenever you attack with one or more Rats, ")
                .setAbilityWord(AbilityWord.THRESHOLD));
    }

    private PersistentMarshstalker(final PersistentMarshstalker card) {
        super(card);
    }

    @Override
    public PersistentMarshstalker copy() {
        return new PersistentMarshstalker(this);
    }
}
