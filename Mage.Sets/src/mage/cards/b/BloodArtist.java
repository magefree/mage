
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.Target;
import mage.target.TargetPlayer;

/**
 *
 * @author noxx
 */
public final class BloodArtist extends CardImpl {

    public BloodArtist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Whenever Blood Artist or another creature dies, target player loses 1 life and you gain 1 life.
        Ability ability = new DiesThisOrAnotherCreatureTriggeredAbility(new LoseLifeTargetEffect(1), false);
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        Target target = new TargetPlayer();
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private BloodArtist(final BloodArtist card) {
        super(card);
    }

    @Override
    public BloodArtist copy() {
        return new BloodArtist(this);
    }
}
