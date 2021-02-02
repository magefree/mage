
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.abilityword.KinshipAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class NightshadeSchemers extends CardImpl {

    public NightshadeSchemers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Kinship - At the beginning of your upkeep, you may look at the top card of your library. If it shares a creature type with Nightshade Schemers, you may reveal it. 
        // If you do, each opponent loses 2 life.
        this.addAbility(new KinshipAbility(new LoseLifeOpponentsEffect(2)));
    }

    private NightshadeSchemers(final NightshadeSchemers card) {
        super(card);
    }

    @Override
    public NightshadeSchemers copy() {
        return new NightshadeSchemers(this);
    }
}
