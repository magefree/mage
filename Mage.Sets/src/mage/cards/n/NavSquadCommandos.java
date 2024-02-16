
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.BattalionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class NavSquadCommandos extends CardImpl {

    public NavSquadCommandos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Battalion — Whenever Nav Squad Commandos and at least two other creatures attack, Nav Squad Commandos gets +1/+1 until end of turn. Untap it.
        Ability ability = new BattalionAbility(new BoostSourceEffect(1,1, Duration.EndOfTurn));
        ability.addEffect(new UntapSourceEffect().setText("untap it"));
        this.addAbility(ability);
    }

    private NavSquadCommandos(final NavSquadCommandos card) {
        super(card);
    }

    @Override
    public NavSquadCommandos copy() {
        return new NavSquadCommandos(this);
    }
}
