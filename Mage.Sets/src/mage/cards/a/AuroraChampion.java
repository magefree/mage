package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterTeamPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AuroraChampion extends CardImpl {

    private static final FilterPermanent filter = new FilterTeamPermanent(SubType.WARRIOR, "your team controls another Warrior");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, false);

    public AuroraChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Aurora Champion attacks, if your team controls another Warrior, tap target creature.
        Ability ability = new AttacksTriggeredAbility(new TapTargetEffect(), false).withInterveningIf(condition);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private AuroraChampion(final AuroraChampion card) {
        super(card);
    }

    @Override
    public AuroraChampion copy() {
        return new AuroraChampion(this);
    }
}
