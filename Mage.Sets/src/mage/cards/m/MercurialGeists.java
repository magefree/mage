
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class MercurialGeists extends CardImpl {

    public MercurialGeists(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{R}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast an instant or sorcery spell, Mercurial Geists gets +3/+0 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new BoostSourceEffect(3, 0, Duration.EndOfTurn), StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY, false));
    }

    public MercurialGeists(final MercurialGeists card) {
        super(card);
    }

    @Override
    public MercurialGeists copy() {
        return new MercurialGeists(this);
    }
}
