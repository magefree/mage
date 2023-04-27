
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventCombatDamageBySourceEffect;
import mage.abilities.effects.common.PreventCombatDamageToSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;


/**
 *
 * @author jeffwadsworth
 */
public final class MoonlightGeist extends CardImpl {

    public MoonlightGeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());

        // {3}{W}: Prevent all combat damage that would be dealt to and dealt by Moonlight Geist this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventCombatDamageToSourceEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{3}{W}"));
        ability.addEffect(new PreventCombatDamageBySourceEffect(Duration.EndOfTurn));
        this.addAbility(ability);
    }

    private MoonlightGeist(final MoonlightGeist card) {
        super(card);
    }

    @Override
    public MoonlightGeist copy() {
        return new MoonlightGeist(this);
    }
}