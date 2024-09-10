package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.GodEternalDiesTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.GodEternalOketraToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GodEternalOketra extends CardImpl {

    public GodEternalOketra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.GOD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Whenever you cast a creature spell, create a 4/4 black Zombie Warrior creature token with vigilance.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new GodEternalOketraToken()),
                StaticFilters.FILTER_SPELL_A_CREATURE, false
        ));

        // When God-Eternal Oketra dies or is put into exile from the battlefield, you may put it into its owner's library third from the top.
        this.addAbility(new GodEternalDiesTriggeredAbility());
    }

    private GodEternalOketra(final GodEternalOketra card) {
        super(card);
    }

    @Override
    public GodEternalOketra copy() {
        return new GodEternalOketra(this);
    }
}
