package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.keyword.RiotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClamorShaman extends CardImpl {

    public ClamorShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Riot
        this.addAbility(new RiotAbility());

        // Whenever Clamor Shaman attacks, target creature an opponent controls can't block this turn.
        Ability ability = new AttacksTriggeredAbility(
                new CantBlockTargetEffect(Duration.EndOfTurn), false
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private ClamorShaman(final ClamorShaman card) {
        super(card);
    }

    @Override
    public ClamorShaman copy() {
        return new ClamorShaman(this);
    }
}
