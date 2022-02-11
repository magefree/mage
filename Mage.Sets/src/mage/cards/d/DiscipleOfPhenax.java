package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DiscipleOfPhenax extends CardImpl {

    public DiscipleOfPhenax(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Disciple of Phenax enters the battlefield, target player reveals a number of cards
        // from their hand equal to your devotion to black. You choose one of them. That player discards that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardCardYouChooseTargetEffect(TargetController.ANY, DevotionCount.B));
        ability.addTarget(new TargetPlayer());
        ability.addHint(DevotionCount.B.getHint());
        this.addAbility(ability);

    }

    private DiscipleOfPhenax(final DiscipleOfPhenax card) {
        super(card);
    }

    @Override
    public DiscipleOfPhenax copy() {
        return new DiscipleOfPhenax(this);
    }
}
