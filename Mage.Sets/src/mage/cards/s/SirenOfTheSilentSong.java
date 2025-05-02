
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.InspiredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author LevelX2
 */
public final class SirenOfTheSilentSong extends CardImpl {

    public SirenOfTheSilentSong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SIREN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // <i>Inspired</i> &mdash; Whenever Siren of the Silent Song becomes untapped, each opponent discards a card, then puts the top card of their library into their graveyard.
        Ability ability = new InspiredAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT));
        Effect effect = new MillCardsEachPlayerEffect(1, TargetController.OPPONENT);
        effect.setText(", then each opponent mills a card");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private SirenOfTheSilentSong(final SirenOfTheSilentSong card) {
        super(card);
    }

    @Override
    public SirenOfTheSilentSong copy() {
        return new SirenOfTheSilentSong(this);
    }
}
