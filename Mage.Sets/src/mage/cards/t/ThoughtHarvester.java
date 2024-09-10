
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ExileCardsFromTopOfLibraryTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class ThoughtHarvester extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a colorless spell");

    static {
        filter.add(ColorlessPredicate.instance);
    }

    public ThoughtHarvester(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a colorless spell, target opponent exiles the top card of their library.
        Ability ability = new SpellCastControllerTriggeredAbility(new ExileCardsFromTopOfLibraryTargetEffect(1), filter, false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private ThoughtHarvester(final ThoughtHarvester card) {
        super(card);
    }

    @Override
    public ThoughtHarvester copy() {
        return new ThoughtHarvester(this);
    }
}
