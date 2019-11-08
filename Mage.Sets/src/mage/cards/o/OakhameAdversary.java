package mage.cards.o;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OakhameAdversary extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("your opponent controls a green permanent");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public OakhameAdversary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // This spell costs {2} less to cast if your opponent controls a green permanent.
        this.addAbility(new SimpleStaticAbility(
                Zone.STACK, new SpellCostReductionSourceEffect(2, condition)
        ).setRuleAtTheTop(true));

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Oakhame Adversary deals combat damage to a player, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false
        ));
    }

    private OakhameAdversary(final OakhameAdversary card) {
        super(card);
    }

    @Override
    public OakhameAdversary copy() {
        return new OakhameAdversary(this);
    }
}
