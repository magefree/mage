package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ruleModifying.CantCastDuringYourTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerGreaterThanBasePowerPredicate;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class KutzilMalametExemplar extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(PowerGreaterThanBasePowerPredicate.instance);
    }

    public KutzilMalametExemplar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Your opponents can't cast spells during your turn.
        this.addAbility(new SimpleStaticAbility(new CantCastDuringYourTurnEffect()));

        // Whenever one or more creatures you control each with power greater than its base power deals combat damage to a player, draw a card.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(new DrawCardSourceControllerEffect(1), filter)
                .setTriggerPhrase("Whenever one or more creatures you control each with power greater than its base power"
                        + " deals combat damage to a player, ")
        );

    }

    private KutzilMalametExemplar(final KutzilMalametExemplar card) {
        super(card);
    }

    @Override
    public KutzilMalametExemplar copy() {
        return new KutzilMalametExemplar(this);
    }
}
