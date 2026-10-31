package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.AttackedThisTurnOpponentsCount;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetsCountAdjuster;
import mage.watchers.common.PlayersAttackedThisTurnWatcher;

/**
 * @author muz
 */
public final class GalvanizingTenor extends CardImpl {

    public GalvanizingTenor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.PERFORMER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Battlefront -- Whenever this creature attacks, goad up to X target creatures, where X is the number of opponents you attacked this turn.
        Ability ability = new AttacksTriggeredAbility(new GoadTargetEffect()
            .setText("goad up to X target creatures, where X is the number of opponents you attacked this turn"))
            .withFlavorWord("Battlefront").addHint(AttackedThisTurnOpponentsCount.getHint());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        ability.setTargetAdjuster(new TargetsCountAdjuster(AttackedThisTurnOpponentsCount.instance));
        this.addAbility(ability, new PlayersAttackedThisTurnWatcher());
    }

    private GalvanizingTenor(final GalvanizingTenor card) {
        super(card);
    }

    @Override
    public GalvanizingTenor copy() {
        return new GalvanizingTenor(this);
    }
}
