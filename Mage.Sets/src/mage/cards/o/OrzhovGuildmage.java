
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class OrzhovGuildmage extends CardImpl {

    public OrzhovGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W/B}{W/B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{W}: Target player gains 1 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeTargetEffect(1), new ManaCostsImpl<>("{2}{W}"));
        ability.addTarget(new TargetPlayer(1));
        this.addAbility(ability);

        // {2}{B}: Each player loses 1 life.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new LoseLifeAllPlayersEffect(1), new ManaCostsImpl<>("{2}{B}")));
    }

    private OrzhovGuildmage(final OrzhovGuildmage card) {
        super(card);
    }

    @Override
    public OrzhovGuildmage copy() {
        return new OrzhovGuildmage(this);
    }
}
