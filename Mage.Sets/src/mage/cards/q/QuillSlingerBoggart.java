
package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.target.TargetPlayer;

/**
 *
 * @author BursegSardaukar
 */
public final class QuillSlingerBoggart extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Kithkin spell");

    static {
        filter.add(SubType.KITHKIN.getPredicate());
    }

    public QuillSlingerBoggart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever a player casts a Kithkin spell, you may have target player lose 1 life.
        Ability ability = new SpellCastAllTriggeredAbility(new LoseLifeTargetEffect(1), filter, true);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private QuillSlingerBoggart(final QuillSlingerBoggart card) {
        super(card);
    }

    @Override
    public QuillSlingerBoggart copy() {
        return new QuillSlingerBoggart(this);
    }
}
