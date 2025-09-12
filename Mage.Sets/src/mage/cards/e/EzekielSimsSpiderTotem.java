package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EzekielSimsSpiderTotem extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SPIDER);

    public EzekielSimsSpiderTotem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // At the beginning of combat on your turn, target Spider you control gets +2/+2 until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new BoostTargetEffect(2, 2));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private EzekielSimsSpiderTotem(final EzekielSimsSpiderTotem card) {
        super(card);
    }

    @Override
    public EzekielSimsSpiderTotem copy() {
        return new EzekielSimsSpiderTotem(this);
    }
}
