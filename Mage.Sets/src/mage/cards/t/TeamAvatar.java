package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TeamAvatar extends CardImpl {

    public TeamAvatar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Whenever a creature you control attacks alone, it gets +X/+X until end of turn, where X is the number of creatures you control.
        this.addAbility(new AttacksAloneControlledTriggeredAbility(
                new BoostTargetEffect(CreaturesYouControlCount.PLURAL, CreaturesYouControlCount.PLURAL)
                        .setText("it gets +X/+X until end of turn, where X is the number of creatures you control"),
                true, false
        ).addHint(CreaturesYouControlHint.instance));

        // {2}{W}, Discard this card: It deals damage equal to the number of creatures you control to target creature.
        Ability ability = new SimpleActivatedAbility(
                Zone.HAND,
                new DamageTargetEffect(CreaturesYouControlCount.PLURAL, "it"),
                new ManaCostsImpl<>("{2}{W}")
        );
        ability.addCost(new DiscardSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private TeamAvatar(final TeamAvatar card) {
        super(card);
    }

    @Override
    public TeamAvatar copy() {
        return new TeamAvatar(this);
    }
}
