package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.GainsChoiceOfAbilitiesEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BallroomBrawlers extends CardImpl {

    public BallroomBrawlers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Whenever Ballroom Brawlers attacks, Ballroom Brawlers and up to one other target creature you control each gain your choice of first strike or lifelink until end of turn.
        Ability ability = new AttacksTriggeredAbility(new GainsChoiceOfAbilitiesEffect(GainsChoiceOfAbilitiesEffect.TargetType.Both,
                FirstStrikeAbility.getInstance(), LifelinkAbility.getInstance())).withRuleTextReplacement(false);
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private BallroomBrawlers(final BallroomBrawlers card) {
        super(card);
    }

    @Override
    public BallroomBrawlers copy() {
        return new BallroomBrawlers(this);
    }
}
