
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DiscardOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class LoxodonSmiter extends CardImpl {

    public LoxodonSmiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{W}");
        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Loxodon Smiter can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // If a spell or ability an opponent controls causes you to discard Loxodon Smiter, put it onto the battlefield instead of putting it into your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.HAND, new DiscardOntoBattlefieldEffect()));
    }

    private LoxodonSmiter(final LoxodonSmiter card) {
        super(card);
    }

    @Override
    public LoxodonSmiter copy() {
        return new LoxodonSmiter(this);
    }
}
